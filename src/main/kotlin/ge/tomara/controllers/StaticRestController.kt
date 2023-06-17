package ge.tomara.controllers

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class StaticRestController {

    @GetMapping(value=["/", "/admin"])
    fun index(): String = "index"

    @GetMapping("/login")
    fun login(): String = "login"

}
