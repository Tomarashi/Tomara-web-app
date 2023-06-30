package ge.tomara.controllers

import ge.tomara.config.HttpSessionListenerWrapper
import ge.tomara.constants.GLOBAL_GROUP
import ge.tomara.entity.words.WordsDeletedEntity
import ge.tomara.entity.words.WordsEntity
import ge.tomara.metrics.MetricsCollectorHolder
import ge.tomara.metrics.MetricsCollectorOffset
import ge.tomara.repository.words.WordsDeletedRepository
import ge.tomara.repository.words.WordsOfferAddRepository
import ge.tomara.repository.words.WordsOfferAddStoreRepository
import ge.tomara.repository.words.WordsOfferDeleteRepository
import ge.tomara.repository.words.WordsRepository
import ge.tomara.response.admin.ActiveSessionCount
import ge.tomara.response.admin.OfferCountResponse
import ge.tomara.response.admin.OfferCountResponseTotalOrDistinct
import ge.tomara.response.admin.ValidCountResponse
import ge.tomara.response.admin.OfferFrequencyResponse
import ge.tomara.response.admin.OfferFrequencyResponseEntry
import ge.tomara.response.admin.StatsTotalResponse
import ge.tomara.response.admin.StatsTotalResponseViewsOrUniques
import ge.tomara.response.general.ErrorMessageResponse
import ge.tomara.response.general.SuccessMessageResponse
import ge.tomara.utils.TypeUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.Date
import java.util.concurrent.locks.ReentrantLock

@RestController
@RequestMapping("$GLOBAL_GROUP/admin")
class AdminRestController {
    companion object {
        private const val STATISTICS_ROUTE_GROUP = "/statistics"
        private const val OFFER_ROUTE_GROUP = "/offer"
        private const val WEB_METRICS_ROUTE_GROUP = "/web-metrics"

        private const val SUCCESS_ADD_OR_DELETE = 1
        private const val ERROR_ADD_OR_DELETE_NO_ID = -1
        private const val ERROR_ADD_OR_DELETE_BAD_DECISION = -2
    }

    private val addOfferResolveLock = ReentrantLock()
    private val deleteOfferResolveLock = ReentrantLock()

    private val timeMetrics = MetricsCollectorHolder.getTimeMetrics()
    private val sessionMetrics = MetricsCollectorHolder.getSessionMetrics()

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

    @GetMapping("$STATISTICS_ROUTE_GROUP/count")
    fun getValidTypeCount(): ValidCountResponse {
        val addCount = wordsRepository.count().toInt()
        val deleteCount = wordsDeletedRepository.count().toInt()
        return ValidCountResponse(addCount, deleteCount)
    }

    @GetMapping("$STATISTICS_ROUTE_GROUP/offer/count")
    fun getOfferTypeCount(): OfferCountResponse {
        val addCount = OfferCountResponseTotalOrDistinct(
            totalCount=wordsOfferAddRepository.count().toInt(),
            distinctCount=wordsOfferAddStoreRepository.count().toInt(),
        )
        val deleteCount = OfferCountResponseTotalOrDistinct(
            totalCount=wordsOfferDeleteRepository.count().toInt(),
            distinctCount=wordsOfferDeleteRepository.getOfferDeleteIdDistinctCount().toInt(),
        )
        return OfferCountResponse(addCount, deleteCount)
    }

    @GetMapping("$OFFER_ROUTE_GROUP/add/frequency")
    fun getOfferAddFrequency(): OfferFrequencyResponse<String> {
        val frequencyCounterByWord = mutableListOf<OfferFrequencyResponseEntry<String>>()
        for(entry in wordsOfferAddRepository.getOfferAddIdFrequency()) {
            val found = wordsOfferAddStoreRepository.findById(entry[0])
            if(found.isPresent) {
                frequencyCounterByWord.add(OfferFrequencyResponseEntry(
                    entry[0], found.get().wordGeo, entry[1],
                ))
            }
        }
        return OfferFrequencyResponse(frequencyCounterByWord)
    }

    @GetMapping("$OFFER_ROUTE_GROUP/delete/frequency")
    fun getOfferDeleteFrequency(): OfferFrequencyResponse<String> {
        val frequencyCounterByWord = mutableListOf<OfferFrequencyResponseEntry<String>>()
        for(entry in wordsOfferDeleteRepository.getOfferDeleteIdFrequency()) {
            val found = wordsRepository.findById(entry[0])
            if(found.isPresent) {
                frequencyCounterByWord.add(OfferFrequencyResponseEntry(
                    entry[0], found.get().wordGeo, entry[1],
                ))
            }
        }
        return OfferFrequencyResponse(frequencyCounterByWord)
    }

    @PostMapping("$OFFER_ROUTE_GROUP/resolve/add")
    fun resolveOfferAdd(
        @RequestParam("id") valueId: Int,
        @RequestParam("decision") decisionStr: String,
    ): ResponseEntity<Any> {
        val decision = try {
            TypeUtils.parseBoolean(decisionStr)
        } catch (e: NumberFormatException) {
            return ResponseEntity.badRequest().body(
                ErrorMessageResponse(
                    ERROR_ADD_OR_DELETE_BAD_DECISION,
                    "Can't convert decision ('${decisionStr}') to boolean")
            )
        }

        synchronized(addOfferResolveLock) {
            val wordsStoreEntity = wordsOfferAddStoreRepository.findById(valueId)
            if(wordsStoreEntity.isEmpty) {
                return ResponseEntity.badRequest().body(
                    ErrorMessageResponse(ERROR_ADD_OR_DELETE_NO_ID, "ID doesn't exist")
                )
            }

            if(decision) {
                val frequency = wordsOfferAddRepository.getOfferAddIdFrequency(valueId)
                wordsRepository.save(WordsEntity.from(
                    wordsStoreEntity.get(), frequency,
                ))
            }
            wordsOfferAddRepository.deleteAllByOfferAddId(valueId)
            wordsOfferAddStoreRepository.deleteById(valueId)
            return ResponseEntity.ok(SuccessMessageResponse(
                SUCCESS_ADD_OR_DELETE, "Add offer ${if(decision) "accepted" else "rejected"}"
            ))
        }
    }

    @PostMapping("$OFFER_ROUTE_GROUP/resolve/delete")
    fun resolveOfferDelete(
        @RequestParam("id") valueId: Int,
        @RequestParam("decision") decisionStr: String,
    ): ResponseEntity<Any> {
        val decision = try {
            TypeUtils.parseBoolean(decisionStr)
        } catch (_: NumberFormatException) {
            return ResponseEntity.badRequest().body(
                ErrorMessageResponse(
                    ERROR_ADD_OR_DELETE_BAD_DECISION,
                    "Can't convert decision ('${decisionStr}') to boolean")
            )
        }

        synchronized(deleteOfferResolveLock) {
            val wordsEntity = wordsRepository.findById(valueId)
            if(wordsEntity.isEmpty) {
                return ResponseEntity.badRequest().body(
                    ErrorMessageResponse(ERROR_ADD_OR_DELETE_NO_ID, "ID doesn't exist")
                )
            }

            wordsOfferDeleteRepository.deleteAllByOfferDeleteId(valueId)
            if(decision) {
                wordsRepository.deleteById(valueId)
                wordsDeletedRepository.save(WordsDeletedEntity.from(wordsEntity.get()))
            }
            return ResponseEntity.ok(SuccessMessageResponse(
                SUCCESS_ADD_OR_DELETE, "Delete offer ${if(decision) "accepted" else "rejected"}"
            ))
        }
    }

    @GetMapping("$WEB_METRICS_ROUTE_GROUP/active-session/count")
    fun getActiveSessionCount(): ActiveSessionCount {
        val count = HttpSessionListenerWrapper.getActiveSessionsSize()
        return ActiveSessionCount(count)
    }

    @GetMapping("$WEB_METRICS_ROUTE_GROUP/total")
    fun getStatsTotal(): StatsTotalResponse {
        val currDate = Date()
        val weekDate = MetricsCollectorOffset.WEEK.offsetFrom(currDate)
        val dayDate = MetricsCollectorOffset.DAY.offsetFrom(currDate)
        val times = StatsTotalResponseViewsOrUniques(
            total=timeMetrics.size(),
            week=timeMetrics.getMetricFrom(weekDate),
            day=timeMetrics.getMetricFrom(dayDate)
        )
        val sessions = StatsTotalResponseViewsOrUniques(
            total=sessionMetrics.size(),
            week=sessionMetrics.getMetricFrom(weekDate),
            day=sessionMetrics.getMetricFrom(dayDate)
        )
        return StatsTotalResponse(views=times, uniques=sessions)
    }

}
