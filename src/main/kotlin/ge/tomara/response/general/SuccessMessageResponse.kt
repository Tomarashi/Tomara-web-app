package ge.tomara.response.general

import com.fasterxml.jackson.annotation.JsonProperty

data class SuccessMessageResponse(
    @JsonProperty("success_msg") val successMsg: String,
)
