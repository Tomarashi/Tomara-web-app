package ge.tomara.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver
import org.thymeleaf.templatemode.TemplateMode
import java.nio.charset.StandardCharsets

@Configuration
class TemplateResolverConfiguration {
    companion object {
        private const val HTML_SUFFIX = ".html"
    }

    @Bean
    fun templateResolverStatic(): SpringResourceTemplateResolver {
        return SpringResourceTemplateResolver().apply {
            prefix = "classpath:/static/"
            suffix = HTML_SUFFIX
            templateMode = TemplateMode.HTML
            characterEncoding = StandardCharsets.UTF_8.name()
            order = 0
            isCacheable = false
            checkExistence = true
        }
    }

    @Bean
    fun templateResolverViews(): SpringResourceTemplateResolver {
        return SpringResourceTemplateResolver().apply {
            prefix = "classpath:/views/"
            suffix = HTML_SUFFIX
            templateMode = TemplateMode.HTML
            characterEncoding = StandardCharsets.UTF_8.name()
            order = 1
            isCacheable = false
            checkExistence = true
        }
    }

    @Bean
    fun templateResolverError(): SpringResourceTemplateResolver {
        return SpringResourceTemplateResolver().apply {
            prefix = "classpath:/static/"
            suffix = HTML_SUFFIX
            templateMode = TemplateMode.HTML
            characterEncoding = StandardCharsets.UTF_8.name()
            order = 2
            isCacheable = false
            checkExistence = true
        }
    }

}
