package ge.tomara.entity

import ge.tomara.repository.WordsOfferAddRepository
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
@Table(name=WordsOfferAddRepository.DATABASE_NAME)
data class WordsOfferAddEntity(
    @Id()
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="add_offer_id")
    var id: Int = Int.MIN_VALUE,
    @Column(name="add_offer_word_id")
    var addOfferWordId: Int,
    @CreationTimestamp
    @Column(name="add_offer_time")
    var addOfferTime: Timestamp = Timestamp(Date().time),
) {
    companion object {
        fun from(storeEntity: WordsOfferAddStoreEntity): WordsOfferAddEntity {
            return WordsOfferAddEntity(addOfferWordId = storeEntity.id)
        }
    }
}
