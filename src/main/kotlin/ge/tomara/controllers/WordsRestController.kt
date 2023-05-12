package ge.tomara.controllers

import ge.tomara.constants.GLOBAL_GROUP
import ge.tomara.repository.WordsDeletedRepository
import ge.tomara.repository.WordsRepository
import ge.tomara.response.WordResponse
import ge.tomara.response.WordResponseType
import ge.tomara.response.WordsFindResponse
import ge.tomara.utils.mergeSortedLists
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class WordsRestController {
    companion object {
        const val API_LOCAL_GROUP_ROUTE = "$GLOBAL_GROUP/word"

        private val WORD_RESPONSE_COMPARATOR = Comparator<WordResponse> { a, b ->
            a.wordGeo.compareTo(b.wordGeo)
        }
    }

    @Autowired
    private lateinit var wordsRepository: WordsRepository
    @Autowired
    private lateinit var wordsDeletedRepository: WordsDeletedRepository

    @GetMapping("$API_LOCAL_GROUP_ROUTE/find")
    fun findWords(
        @RequestParam("sub_geo_word", required=true) subString: String,
        @RequestParam("n_limit", required=false) nLimitArg: Int? = null,
    ): WordsFindResponse<WordResponse> {
        val nLimit = if(nLimitArg == null || nLimitArg < 0) Int.MAX_VALUE else nLimitArg
        val validWords = wordsRepository.findByGeoWordContains(subString, nLimit).map {wordsEntity ->
            WordResponse.from(wordsEntity, WordResponseType.VALID)
        }
        val deletedWords = wordsDeletedRepository.findByDelGeoWordContains(subString, nLimit).map {wordsEntity ->
            WordResponse.from(wordsEntity, WordResponseType.DELETED)
        }

        val result = mergeSortedLists(validWords, deletedWords, nLimit, WORD_RESPONSE_COMPARATOR)
        return WordsFindResponse(result)
    }

}
