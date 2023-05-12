package ge.tomara.controllers

import ge.tomara.constants.GLOBAL_GROUP
import ge.tomara.repository.WordsRepository
import ge.tomara.response.StatisticsResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class StatisticsRestController {
    companion object {
        const val API_LOCAL_GROUP_ROUTE = "$GLOBAL_GROUP/statistics"
    }

    @Autowired
    private lateinit var wordsRepository: WordsRepository

    @GetMapping("$API_LOCAL_GROUP_ROUTE/")
    fun statistics(): StatisticsResponse {
        return StatisticsResponse(wordsRepository.count().toInt())
    }

}
