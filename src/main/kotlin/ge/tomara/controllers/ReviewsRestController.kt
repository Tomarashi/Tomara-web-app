package ge.tomara.controllers

import com.fasterxml.jackson.annotation.JsonProperty
import ge.tomara.constants.GLOBAL_GROUP
import ge.tomara.entity.reviews.ReviewsEntity
import ge.tomara.repository.reviews.ReviewsRepository
import ge.tomara.response.general.SuccessMessageResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("$GLOBAL_GROUP/reviews")
class ReviewsRestController {
    companion object {
        data class AddReviewBody(
            @JsonProperty("content") val content: String,
        )
    }

    @Autowired
    private lateinit var reviewsRepository: ReviewsRepository

    @PostMapping("/add")
    fun addReview(@RequestBody body: AddReviewBody): SuccessMessageResponse {
        reviewsRepository.save(ReviewsEntity(content=body.content))
        return SuccessMessageResponse("Review added successfully")
    }

}
