package ge.tomara.controllers

import ge.tomara.constants.GLOBAL_GROUP
import ge.tomara.repository.WordsRepository
import ge.tomara.response.statistics.StatisticsResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("$GLOBAL_GROUP/statistics")
class StatisticsRestController {

    @Autowired
    private lateinit var wordsRepository: WordsRepository

    @GetMapping("/word_count")
    fun statistics(): StatisticsResponse {
        return StatisticsResponse(wordsRepository.count().toInt())
    }

}
