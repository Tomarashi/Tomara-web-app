package ge.tomara.response.statistics

import com.fasterxml.jackson.annotation.JsonProperty

data class StatisticsResponse(
    @JsonProperty("total_word_count")
    val totalWordCount: Int,
)
