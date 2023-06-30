package ge.tomara.repository.reviews

import ge.tomara.entity.reviews.ReviewsEntity
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface ReviewsRepository: CrudRepository<ReviewsEntity, Int> {
    companion object {
        const val TABLE_NAME = "reviews"

        private const val SORT_WITH_COLUMN = "review_time"
    }

    @Query(value="""
        SELECT * FROM $TABLE_NAME
        ORDER BY $SORT_WITH_COLUMN
        LIMIT :offset, :n
    """, nativeQuery=true)
    fun getReviewsInRange(@Param("offset") offset: Int, @Param("n") n: Int): List<ReviewsEntity>

}
