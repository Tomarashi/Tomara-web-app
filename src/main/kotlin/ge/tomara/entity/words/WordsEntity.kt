package ge.tomara.entity.words

import ge.tomara.repository.words.WordsRepository
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name= WordsRepository.DATABASE_NAME)
data class WordsEntity(
    @Id()
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="word_id")
    var id: Int = Int.MIN_VALUE,
    @Column(name="word_geo", length=255, nullable=false, unique=false)
    var wordGeo: String,
    @Column(name="word_eng", length=255, nullable=false, unique=false)
    var wordEng: String,
    @Column(name="frequency", nullable=false, unique=false)
    var frequency: Int,
) {
    companion object {
        @JvmStatic
        fun from(entity: WordsOfferAddStoreEntity, frequency: Int): WordsEntity {
            return WordsEntity(
                wordGeo=entity.wordGeo,
                wordEng=entity.wordEng,
                frequency=frequency,
            )
        }
    }
}
