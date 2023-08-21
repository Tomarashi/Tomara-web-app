package ge.tomara.entity.words

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class AnyWordsEntity(
    @Id()
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    var id: Int = Int.MIN_VALUE,
    @Column(name="word_geo", length=255, nullable=false, unique=false)
    var wordGeo: String,
    @Column(name="word_type")
    var wordType: Int,
) {
    companion object {
        const val WORD_VALID = 0
        const val WORD_DELETED = 1
    }
}
