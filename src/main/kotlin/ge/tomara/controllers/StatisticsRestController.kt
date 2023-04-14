package ge.tomara.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class StatisticsRestController {
    companion object {
        const val API_GROUP_ROUTE = "/api"
    }

    @GetMapping("$API_GROUP_ROUTE/")
    fun helloWorld(): String {
        return "Hello World"
    }

}
