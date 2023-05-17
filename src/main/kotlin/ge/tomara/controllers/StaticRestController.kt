package ge.tomara.controllers

import org.springframework.web.bind.annotation.GetMapping

class StaticRestController {

    @GetMapping("/")
    fun index(): String {
        return "index"
    }

}
