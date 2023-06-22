package ge.tomara.config

import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.security.web.firewall.RequestRejectedException
import org.springframework.stereotype.Component
import org.springframework.web.filter.GenericFilterBean
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletResponse

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
class RequestRejectedExceptionFilter: GenericFilterBean() {
    override fun doFilter(
        request: ServletRequest, response: ServletResponse, chain: FilterChain,
    ) {
        try {
            chain.doFilter(request, response)
        } catch(_: RequestRejectedException) {
            val httpResponse = response as HttpServletResponse
            httpResponse.sendError(HttpServletResponse.SC_NOT_FOUND)
        }
    }
}
