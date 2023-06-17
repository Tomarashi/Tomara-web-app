package ge.tomara.controllers

import ge.tomara.constants.GLOBAL_GROUP
import ge.tomara.repository.words.WordsDeletedRepository
import ge.tomara.repository.words.WordsOfferAddRepository
import ge.tomara.repository.words.WordsOfferAddStoreRepository
import ge.tomara.repository.words.WordsOfferDeleteRepository
import ge.tomara.repository.words.WordsRepository
import ge.tomara.response.admin.AllCountResponse
import ge.tomara.response.admin.OfferAllCountResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("$GLOBAL_GROUP/admin")
class AdminRestController {
    companion object {
        private const val OFFER_ROUTE_GROUP = "/offer"
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

    @GetMapping("/all/count")
    fun getAllTypeCount(): AllCountResponse {
        val addCount = wordsRepository.count().toInt()
        val deleteCount = wordsDeletedRepository.count().toInt()
        return AllCountResponse(addCount, deleteCount)
    }

    @GetMapping("$OFFER_ROUTE_GROUP/all/count")
    fun getOfferAllTypeCount(): OfferAllCountResponse {
        val addCount = wordsOfferAddRepository.count().toInt()
        val deleteCount = wordsOfferDeleteRepository.count().toInt()
        return OfferAllCountResponse(addCount, deleteCount)
    }

    @GetMapping("$OFFER_ROUTE_GROUP/delete/frequency")
    fun getGroups(): ResponseEntity<Any> {
        return ResponseEntity.ok("Hello")
    }

}
