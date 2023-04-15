package ge.tomara.controllers

import ge.tomara.repository.WordsRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class WordsRestController {
    companion object {
        const val API_LOCAL_GROUP_ROUTE = "$GLOBAL_GROUP/word"
    }

    @Autowired
    private lateinit var wordsRepository: WordsRepository

    @GetMapping("${API_LOCAL_GROUP_ROUTE}/random")
    fun randomWord(): String {
        val result = wordsRepository.findById(1)
        if(result.isEmpty) {
            return "Couldn't find a result"
        }
        return result.get().toString()
    }

}
