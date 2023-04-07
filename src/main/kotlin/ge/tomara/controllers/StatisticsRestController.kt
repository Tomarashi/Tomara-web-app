package ge.tomara.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class StatisticsRestController {

    @GetMapping("/")
    fun helloWorld(): String {
        return "Hello World"
    }

}
