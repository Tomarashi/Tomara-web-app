package ge.tomara.entity.words

import ge.tomara.repository.words.WordsOfferDeleteRepository
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
@Table(name= WordsOfferDeleteRepository.DATABASE_NAME)
data class WordsOfferDeleteEntity(
    @Id()
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="del_offer_id")
    var id: Int = Int.MIN_VALUE,
    @Column(name="del_offer_word_id")
    var delOfferWordId: Int,
    @CreationTimestamp
    @Column(name="del_offer_time")
    var delOfferTime: Timestamp = Timestamp(Date().time),
) {
    companion object {
        fun from(wordsEntity: WordsEntity): WordsOfferDeleteEntity {
            return WordsOfferDeleteEntity(delOfferWordId = wordsEntity.id)
        }
    }
}
