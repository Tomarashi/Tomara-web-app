package ge.tomara.entity.words

import ge.tomara.repository.words.WordsDeletedRepository
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name= WordsDeletedRepository.DATABASE_NAME)
data class WordsDeletedEntity(
    @Id()
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="deletion_id")
    var id: Int = Int.MIN_VALUE,
    @Column(name="del_word_id")
    var delWordId: Int,
    @Column(name="del_word_geo", length=255, nullable=false, unique=false)
    var delWordGeo: String,
    @Column(name="del_word_eng", length=255, nullable=false, unique=false)
    var delWordEng: String,
) {
    companion object {
        @JvmStatic
        fun from(entity: WordsEntity): WordsDeletedEntity {
            return WordsDeletedEntity(
                delWordId=entity.id,
                delWordGeo=entity.wordGeo,
                delWordEng=entity.wordEng,
            )
        }
    }
}
