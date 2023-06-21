package ge.tomara.config

import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping

@Component
class SpringBootMappingsListener: ApplicationListener<ContextRefreshedEvent> {
    companion object {
        private val methodsOrderedMap = listOf(
            RequestMethod.HEAD,
            RequestMethod.GET,
            RequestMethod.POST,
            RequestMethod.PUT,
            RequestMethod.DELETE,
        ).mapIndexed { i, method -> method to i }.toMap()
        private val maxMethodLen = methodsOrderedMap.keys.map { m -> m.name.length}.max()
    }

    override fun onApplicationEvent(event: ContextRefreshedEvent) {
        val methodsRoutesList = mutableListOf<Pair<RequestMethod, String>>().apply {
            val handlerMethods = event
                .applicationContext
                .getBean(RequestMappingHandlerMapping::class.java)
                .handlerMethods

            for(mapping in handlerMethods) {
                val methods = mapping.key.methodsCondition.methods
                val directPaths = mapping.key.directPaths
                for(method in methods) {
                    for(path in directPaths) {
                        add(Pair(method, path))
                    }
                }
            }
        }

        methodsRoutesList.sortWith { a, b ->
            if(a.first == b.first) {
                a.second.compareTo(b.second)
            } else {
                val aMethod = methodsOrderedMap[a.first] ?: Int.MIN_VALUE
                val bMethod = methodsOrderedMap[b.first] ?: Int.MIN_VALUE
                aMethod - bMethod
            }
        }

        val methodWithPaths = methodsRoutesList.joinToString("\n") { pair ->
            val methodName = pair.first.name
            val spaces = " ".repeat(maxMethodLen - methodName.length + 1)
            "$methodName$spaces${pair.second}"
        }
        println("Method Path\n$methodWithPaths")
    }

}
