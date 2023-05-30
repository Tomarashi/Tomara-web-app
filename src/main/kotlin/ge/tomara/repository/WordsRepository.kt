package ge.tomara.repository

import ge.tomara.entity.WordsEntity
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface WordsRepository: CrudRepository<WordsEntity, Int> {
    companion object {
        const val DATABASE_NAME = "words"
    }

    @Query(value="SELECT * FROM $DATABASE_NAME WHERE word_geo LIKE %?1%", nativeQuery=true)
    fun findByGeoWordContains(subGeoWord: String): List<WordsEntity>

    @Query(value="SELECT * FROM $DATABASE_NAME WHERE word_geo LIKE %?1% LIMIT ?2", nativeQuery=true)
    fun findByGeoWordContains(subGeoWord: String, nLimit: Int): List<WordsEntity>
}
