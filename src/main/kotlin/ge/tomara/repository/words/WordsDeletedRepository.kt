package ge.tomara.repository.words

import ge.tomara.entity.words.WordsDeletedEntity
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface WordsDeletedRepository: CrudRepository<WordsDeletedEntity, Int> {
    companion object {
        const val DATABASE_NAME = "words_deleted"
        private const val DEL_WORD_GEO_COL = "del_word_geo"
    }

    @Query(
        value="SELECT COUNT(*) FROM $DATABASE_NAME WHERE $DEL_WORD_GEO_COL LIKE %?1%",
        nativeQuery=true,
    )
    fun countByDelGeoWordContains(subGeoWord: String): Long

    @Query(
        value="SELECT * FROM $DATABASE_NAME WHERE $DEL_WORD_GEO_COL LIKE %?1% ORDER BY $DEL_WORD_GEO_COL LIMIT ?2",
        nativeQuery=true,
    )
    fun findByDelGeoWordContains(subGeoWord: String, nLimit: Int): List<WordsDeletedEntity>
}
