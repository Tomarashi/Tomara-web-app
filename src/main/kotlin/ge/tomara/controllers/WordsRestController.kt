package ge.tomara.controllers

import ge.tomara.constants.GEORGIAN_CHARACTERS
import ge.tomara.constants.GEORGIAN_CHARACTER_ENG_EQUIVALENT
import ge.tomara.constants.GLOBAL_GROUP
import ge.tomara.repository.words.WordsDeletedRepository
import ge.tomara.repository.words.WordsOfferAddRepository
import ge.tomara.repository.words.WordsOfferAddStoreRepository
import ge.tomara.repository.words.WordsOfferDeleteRepository
import ge.tomara.repository.words.WordsRepository
import ge.tomara.entity.words.WordsOfferAddEntity
import ge.tomara.entity.words.WordsOfferAddStoreEntity
import ge.tomara.entity.words.WordsOfferDeleteEntity
import ge.tomara.response.general.ErrorMessageResponse
import ge.tomara.response.general.SuccessMessageResponse
import ge.tomara.response.words.WordResponse
import ge.tomara.response.words.WordResponseType
import ge.tomara.response.words.WordsFindResponse
import ge.tomara.utils.mergeSortedLists
import ge.tomara.utils.zipLists
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.lang.StringBuilder
import java.util.concurrent.locks.ReentrantLock

@RestController
@RequestMapping("$GLOBAL_GROUP/word")
class WordsRestController {
    companion object {
        const val ERROR_MSG_INVALID_WORD_ID = "Invalid word's ID"

        const val SUCCESS_MSG_ADD_OFFER_STORED = "Offer to add is Stored"
        const val SUCCESS_MSG_DEL_OFFER_STORED = "Offer to delete is Stored"

        @JvmStatic
        private val WORD_RESPONSE_COMPARATOR = Comparator<WordResponse> { a, b ->
            a.wordGeo.compareTo(b.wordGeo)
        }

        @JvmStatic
        private val GEO_TO_ENG_CHAR_MAP = zipLists(
            GEORGIAN_CHARACTERS.toList(),
            GEORGIAN_CHARACTER_ENG_EQUIVALENT.toList(),
        ).toMap()

        @JvmStatic
        private fun convertGeoToEngWord(word: String): String {
            return StringBuilder(word.length).apply {
                for(c in word) {
                    append(GEO_TO_ENG_CHAR_MAP[c])
                }
            }.toString()
        }
    }

    @Autowired
    private lateinit var wordsRepository: WordsRepository
    @Autowired
    private lateinit var wordsDeletedRepository: WordsDeletedRepository
    @Autowired
    private lateinit var wordsOfferAddRepository: WordsOfferAddRepository
    @Autowired
    private lateinit var wordsOfferDeleteRepository: WordsOfferDeleteRepository
    @Autowired
    private lateinit var wordsOfferAddStoreRepository: WordsOfferAddStoreRepository

    private val addWordOfferLock = ReentrantLock()
    private val deleteWordOfferLock = ReentrantLock()

    @GetMapping("/find")
    fun findWords(
        @RequestParam("sub_geo_word", required=true) subGeoWord: String,
        @RequestParam("n_limit", required=false) nLimitArg: Int? = null,
        @RequestParam("request_id", required=false) requestId: String? = null,
    ): ResponseEntity<Any> {
        val nLimit = if(nLimitArg == null || nLimitArg < 0) Int.MAX_VALUE else nLimitArg

        if(subGeoWord.isBlank()) {
            return ResponseEntity.badRequest().body(
                WordsFindResponse(emptyList<WordResponse>(), false, 0, requestId = requestId),
            )
        }

        val startTimeMS = System.currentTimeMillis()
        val validWords = wordsRepository.findByGeoWordContains(subGeoWord, nLimit).map {wordsEntity ->
            WordResponse.from(wordsEntity, WordResponseType.VALID)
        }
        val deletedWords = ArrayList<WordResponse>(nLimit).apply {
            val usedIds = HashSet<Int>()
            val result = wordsDeletedRepository.findByDelGeoWordContains(subGeoWord, nLimit).filter { word ->
                if(usedIds.contains(word.delWordId)) {
                    false
                } else {
                    usedIds.add(word.delWordId)
                    true
                }
            }
            addAll(result.map {wordsEntity ->
                WordResponse.from(wordsEntity, WordResponseType.DELETED)
            })
        }
        val containsExact = wordsRepository.isExactGeoWord(subGeoWord) > 0
        val allValidWordsCount = wordsRepository.countByGeoWordContains(subGeoWord)
        val allDeletedWordsCount = wordsDeletedRepository.countByDelGeoWordContains(subGeoWord)
        val allWordsCount = allValidWordsCount + allDeletedWordsCount

        val result = mergeSortedLists(validWords, deletedWords, nLimit, WORD_RESPONSE_COMPARATOR)
        val endTimeMS = System.currentTimeMillis()

        val response = WordsFindResponse(
            result, containsExact,
            allWordsCount,
            endTimeMS - startTimeMS,
            requestId,
        )
        return ResponseEntity.ok(response)
    }

    @PostMapping("/offer/add")
    fun addWordOffer(
        @RequestParam("new_word", required=true) newWord: String,
    ): ResponseEntity<Any> {
        synchronized(addWordOfferLock) {
            var storeEntity = wordsOfferAddStoreRepository.findByWordGeo(newWord)
            if(storeEntity == null) {
                storeEntity = wordsOfferAddStoreRepository.save(
                    WordsOfferAddStoreEntity(
                        wordGeo=newWord,
                        wordEng=convertGeoToEngWord(newWord),
                    ),
                )
            }

            wordsOfferAddRepository.save(
                WordsOfferAddEntity.from(storeEntity),
            )
            return ResponseEntity.ok(
                SuccessMessageResponse(SUCCESS_MSG_ADD_OFFER_STORED),
            )
        }
    }

    @PostMapping("/offer/delete")
    fun deleteWordOffer(
        @RequestParam("word_id", required=true) delWordId: Int,
    ): ResponseEntity<Any> {
        synchronized(deleteWordOfferLock) {
            val wordsEntity = wordsRepository.findById(delWordId)
            if(wordsEntity.isEmpty) {
                return ResponseEntity.badRequest().body(
                    ErrorMessageResponse(ERROR_MSG_INVALID_WORD_ID)
                )
            }
            wordsOfferDeleteRepository.save(
                WordsOfferDeleteEntity.from(
                wordsEntity.get(),
            ))
            return ResponseEntity.ok(
                SuccessMessageResponse(SUCCESS_MSG_DEL_OFFER_STORED),
            )
        }
    }

}
