package ge.tomara.controllers

import ge.tomara.metrics.MetricsCollectorHolder
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.context.request.RequestContextHolder
import javax.servlet.http.HttpServletRequest

@Controller
class StaticRestController {
    private val timeMetrics = MetricsCollectorHolder.getTimeMetrics()
    private val sessionMetrics = MetricsCollectorHolder.getSessionMetrics()

    @GetMapping(value=["/", "/admin"])
    fun index(request: HttpServletRequest): String {
        if(!request.requestURI.startsWith("/admin")) {
            timeMetrics.storeTimePoint()
            sessionMetrics.storeSession(
                RequestContextHolder.currentRequestAttributes().sessionId,
            )
        }
        return "index"
    }

    @GetMapping("/login")
    fun login(): String = "login"

}
