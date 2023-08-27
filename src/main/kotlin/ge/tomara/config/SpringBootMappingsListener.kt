package ge.tomara.config

import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping

// @Component
@Deprecated("Doesn't list all possible mappings")
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
        val methodsRoutesList = mutableListOf<Pair<RequestMethod, String>>()
        event
            .applicationContext
            .getBean(RequestMappingHandlerMapping::class.java)
            .handlerMethods
            .forEach { mapping ->
                val methods = mapping.key.methodsCondition.methods
                val directPaths = mapping.key.directPaths
                for(method in methods) {
                    for(path in directPaths) {
                        methodsRoutesList.add(Pair(method, path))
                    }
                }
            }

        methodsRoutesList.sortWith { a, b ->
            val compared = a.second.compareTo(b.second)
            if(compared != 0) {
                compared
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
