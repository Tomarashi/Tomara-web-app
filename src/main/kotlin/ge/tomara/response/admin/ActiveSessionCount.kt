package ge.tomara.response.admin

import com.fasterxml.jackson.annotation.JsonProperty

data class ActiveSessionCount(
    @JsonProperty("count") val count: Int,
)
