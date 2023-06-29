package ge.tomara.entity.words

import ge.tomara.repository.words.WordsOfferAddStoreRepository
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name= WordsOfferAddStoreRepository.DATABASE_NAME)
data class WordsOfferAddStoreEntity(
    @Id()
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="offer_id")
    var id: Int = Int.MIN_VALUE,
    @Column(name="word_geo", length=255, nullable=false, unique=false)
    var wordGeo: String,
    @Column(name="word_eng", length=255, nullable=false, unique=false)
    var wordEng: String,
)
