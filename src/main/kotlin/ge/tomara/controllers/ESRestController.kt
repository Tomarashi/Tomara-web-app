package ge.tomara.controllers

import ge.tomara.constants.GLOBAL_GROUP
import ge.tomara.entity.es.ESWordsEntity
import ge.tomara.repository.es.ESWordsRepository
import ge.tomara.response.es.ESWordExistsResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("$GLOBAL_GROUP/es")
class ESRestController {

    @Autowired
    private lateinit var esWordsRepository: ESWordsRepository

    @GetMapping("/exists/{word_id}")
    fun wordExists(@PathVariable("word_id") wordId: String?): ESWordExistsResponse {
        if(wordId == null) {
            return ESWordExistsResponse(false)
        }
        val entity = esWordsRepository.findById(wordId)
        return ESWordExistsResponse(entity.isPresent)
    }

}
