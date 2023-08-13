package ge.tomara.response.reviews

import com.fasterxml.jackson.annotation.JsonProperty

data class GetReviewsCountResponse(
    @JsonProperty("count") var count: Int,
)
