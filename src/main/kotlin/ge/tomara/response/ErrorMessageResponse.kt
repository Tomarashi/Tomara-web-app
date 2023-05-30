package ge.tomara.response

import com.fasterxml.jackson.annotation.JsonProperty

data class ErrorMessageResponse(
    @JsonProperty("error_msg") val errorMsg: String,
)
