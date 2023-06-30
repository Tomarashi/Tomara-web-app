package ge.tomara.response.words

import com.fasterxml.jackson.annotation.JsonProperty

data class WordsFindResponse<T>(
    @JsonProperty("words") val words: List<T>,
    @JsonProperty("request_id") val requestId: String? = null,
)
