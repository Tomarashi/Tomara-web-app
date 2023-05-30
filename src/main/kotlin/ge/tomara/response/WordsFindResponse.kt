package ge.tomara.response

import com.fasterxml.jackson.annotation.JsonProperty

data class WordsFindResponse<T>(
    @JsonProperty("words") val words: List<T>,
    @JsonProperty("words_size") val wordsSize: Int = words.size,
    @JsonProperty("request_id") val requestId: String? = null,
)
