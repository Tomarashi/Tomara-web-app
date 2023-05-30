package ge.tomara.entity

import ge.tomara.repository.WordsDeletedRepository
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name=WordsDeletedRepository.DATABASE_NAME)
data class WordsDeletedEntity(
    @Id()
    @Column(name="deletion_id")
    var id: Int,
    @Column(name="del_word_id")
    var delWordId: Int,
    @Column(name="del_word_geo", length=255, nullable=false, unique=false)
    var delWordGeo: String,
    @Column(name="del_word_eng", length=255, nullable=false, unique=false)
    var delWordEng: String,
)
