package ge.tomara.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info

class SpringBootMappingsSwagger {
    companion object {
        const val SWAGGER_TITLE = "Tomara Application"
        const val SWAGGER_DESC = "API endpoints for Tomara Application"
        const val SWAGGER_VER = "v1.0.0"
    }

    fun openApi(): OpenAPI {
        return OpenAPI().info(
            Info().title(SWAGGER_TITLE)
                .description(SWAGGER_DESC)
                .version(SWAGGER_VER)
        )
    }
}
