package ge.tomara.response.words

import com.fasterxml.jackson.annotation.JsonProperty

data class WordsFindResponse<T>(
    @JsonProperty("words") val words: List<T>,
    @JsonProperty("words_n_uncut") val wordsNUncut: Long,
    @JsonProperty("time_ms") val timeMS: Long = 0,
    @JsonProperty("request_id") val requestId: String? = null,
)
