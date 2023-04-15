package ge.tomara.repository.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name="WORDS")
data class WordsEntity(
    @Id()
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="word_id")
    var id: Int,
    @Column(name="word_geo", length=255, nullable=false, unique=false)
    var wordGeo: String,
    @Column(name="word_eng", length=255, nullable=false, unique=false)
    var wordEng: String,
    @Column(name="frequency", nullable=false, unique=false)
    var frequency: Int,
    @Column(name="word_status", nullable=false, unique=false)
    var wordStatus: Byte,
)
