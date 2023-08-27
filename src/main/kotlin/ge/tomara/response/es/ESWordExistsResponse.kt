package ge.tomara.response.es

import com.fasterxml.jackson.annotation.JsonProperty

data class ESWordExistsResponse(
    @JsonProperty("exists") val exists: Boolean,
)
