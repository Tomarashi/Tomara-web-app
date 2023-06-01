package ge.tomara.controllers

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class StaticRestController {

    @GetMapping("/")
    fun index(): String = "index"

    @GetMapping("/admin")
    fun admin(): String = "index"

}
