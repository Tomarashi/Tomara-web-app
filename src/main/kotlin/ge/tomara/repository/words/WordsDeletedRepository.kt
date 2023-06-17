package ge.tomara.repository.words

import ge.tomara.entity.words.WordsDeletedEntity
import ge.tomara.entity.words.WordsEntity
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface WordsDeletedRepository: CrudRepository<WordsDeletedEntity, Int> {
    companion object {
        const val DATABASE_NAME = "words_deleted"
    }

    @Query(value="SELECT * FROM $DATABASE_NAME WHERE del_word_geo LIKE %?1%", nativeQuery=true)
    fun findByDelGeoWordContains(subGeoWord: String): List<WordsEntity>

    @Query(value="SELECT * FROM $DATABASE_NAME WHERE del_word_geo LIKE %?1% LIMIT ?2", nativeQuery=true)
    fun findByDelGeoWordContains(subGeoWord: String, nLimit: Int): List<WordsEntity>
}
