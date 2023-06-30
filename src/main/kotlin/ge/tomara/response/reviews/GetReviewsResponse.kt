package ge.tomara.response.reviews

import com.fasterxml.jackson.annotation.JsonProperty
import ge.tomara.entity.reviews.ReviewsEntity

data class GetReviewsResponse(
    @JsonProperty("reviews") var reviews: List<ReviewsResponse>
) {
    companion object {
        @JvmStatic
        fun from(entities: List<ReviewsEntity>): GetReviewsResponse {
            return GetReviewsResponse(entities.map { entity ->
                ReviewsResponse.from(entity)
            })
        }
    }
}
