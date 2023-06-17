package ge.tomara.config

import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.stereotype.Component
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping

@Component
class SpringBootMappingsListener: ApplicationListener<ContextRefreshedEvent> {

    override fun onApplicationEvent(event: ContextRefreshedEvent) {
        val mappings = event
            .applicationContext
            .getBean(RequestMappingHandlerMapping::class.java)
            .handlerMethods
            .map { mapping -> mapping.key.toString() }
            .joinToString("\n")
        println(mappings)
    }

}
