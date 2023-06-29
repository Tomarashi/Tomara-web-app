package ge.tomara.entity.reviews

import ge.tomara.repository.reviews.ReviewsRepository
import org.hibernate.annotations.CreationTimestamp
import java.sql.Timestamp
import java.util.Date
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name=ReviewsRepository.TABLE_NAME)
data class ReviewsEntity(
    @Id()
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="review_id")
    var id: Int = Int.MIN_VALUE,
    @Column(name="review_content", length=1023, nullable=false, unique=false)
    var content: String,
    @CreationTimestamp
    @Column(name="review_time")
    var time: Timestamp = Timestamp(Date().time),
)
