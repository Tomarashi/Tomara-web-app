package ge.tomara.controllers

import ge.tomara.constants.GLOBAL_GROUP
import ge.tomara.repository.WordsDeletedRepository
import ge.tomara.repository.WordsOfferAddRepository
import ge.tomara.repository.WordsOfferAddStoreRepository
import ge.tomara.repository.WordsOfferDeleteRepository
import ge.tomara.repository.WordsRepository
import ge.tomara.entity.WordsOfferAddEntity
import ge.tomara.entity.WordsOfferAddStoreEntity
import ge.tomara.entity.WordsOfferDeleteEntity
import ge.tomara.response.general.ErrorMessageResponse
import ge.tomara.response.general.SuccessMessageResponse
import ge.tomara.response.words.WordResponse
import ge.tomara.response.words.WordResponseType
import ge.tomara.response.words.WordsFindResponse
import ge.tomara.utils.mergeSortedLists
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.locks.ReentrantLock

@RestController
class WordsRestController {
    companion object {
        const val API_LOCAL_GROUP_ROUTE = "$GLOBAL_GROUP/word"
        const val API_LOCAL_OFFER_GROUP_ROUTE = "$API_LOCAL_GROUP_ROUTE/offer"

        const val ERROR_MSG_INVALID_WORD_ID = "Invalid word's ID"

        const val SUCCESS_MSG_ADD_OFFER_STORED = "Offer to add is Stored"
        const val SUCCESS_MSG_DEL_OFFER_STORED = "Offer to delete is Stored"

        private val WORD_RESPONSE_COMPARATOR = Comparator<WordResponse> { a, b ->
            a.wordGeo.compareTo(b.wordGeo)
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

    @GetMapping("$API_LOCAL_GROUP_ROUTE/find")
    fun findWords(
        @RequestParam("sub_geo_word", required=true) subString: String,
        @RequestParam("n_limit", required=false) nLimitArg: Int? = null,
        @RequestParam("request_id", required=false) requestId: String? = null,
    ): ResponseEntity<Any> {
        val nLimit = if(nLimitArg == null || nLimitArg < 0) Int.MAX_VALUE else nLimitArg

        if(subString.isBlank()) {
            return ResponseEntity.badRequest().body(
                WordsFindResponse(emptyList<WordResponse>(), requestId = requestId),
            )
        }

        val validWords = wordsRepository.findByGeoWordContains(subString, nLimit).map {wordsEntity ->
            WordResponse.from(wordsEntity, WordResponseType.VALID)
        }
        val deletedWords = wordsDeletedRepository.findByDelGeoWordContains(subString, nLimit).map {wordsEntity ->
            WordResponse.from(wordsEntity, WordResponseType.DELETED)
        }

        val result = mergeSortedLists(validWords, deletedWords, nLimit, WORD_RESPONSE_COMPARATOR)
        return ResponseEntity.ok(
            WordsFindResponse(result, requestId = requestId),
        )
    }

    @PostMapping("$API_LOCAL_OFFER_GROUP_ROUTE/add")
    fun addWordOffer(
        @RequestParam("new_word", required=true) newWord: String,
    ): ResponseEntity<Any> {
        synchronized(addWordOfferLock) {
            var storeEntity = wordsOfferAddStoreRepository.findByWordGeo(newWord)
            if(storeEntity == null) {
                storeEntity = wordsOfferAddStoreRepository.save(
                    WordsOfferAddStoreEntity(wordGeo = newWord),
                )
            }

            wordsOfferAddRepository.save(WordsOfferAddEntity.from(
                storeEntity,
            ))
            return ResponseEntity.ok(
                SuccessMessageResponse(SUCCESS_MSG_ADD_OFFER_STORED),
            )
        }
    }

    @PostMapping("$API_LOCAL_OFFER_GROUP_ROUTE/delete")
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
            wordsOfferDeleteRepository.save(WordsOfferDeleteEntity.from(
                wordsEntity.get(),
            ))
            return ResponseEntity.ok(
                SuccessMessageResponse(SUCCESS_MSG_DEL_OFFER_STORED),
            )
        }
    }

}
