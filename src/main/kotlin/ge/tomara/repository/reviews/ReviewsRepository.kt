package ge.tomara.repository.reviews

import ge.tomara.entity.reviews.ReviewsEntity
import org.springframework.data.repository.CrudRepository

interface ReviewsRepository: CrudRepository<ReviewsEntity, Int> {
    companion object {
        const val TABLE_NAME = "reviews"
    }
}
