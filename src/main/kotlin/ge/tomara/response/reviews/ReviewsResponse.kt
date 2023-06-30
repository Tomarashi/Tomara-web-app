package ge.tomara.response.reviews

import com.fasterxml.jackson.annotation.JsonProperty
import ge.tomara.entity.reviews.ReviewsEntity

data class ReviewsResponse(
    @JsonProperty("id") var id: Int = Int.MIN_VALUE,
    @JsonProperty("content") var content: String,
    @JsonProperty("time") var time: Long,
) {
    companion object {
        @JvmStatic
        fun from(entity: ReviewsEntity): ReviewsResponse {
            return ReviewsResponse(entity.id, entity.content, entity.time.time)
        }
    }
}
