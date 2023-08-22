package ge.tomara.repository.words

import ge.tomara.entity.words.WordsEntity
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface WordsRepository: CrudRepository<WordsEntity, Int> {
    companion object {
        const val DATABASE_NAME = "words"
        private const val WORD_GEO_COL = "word_geo"
    }

    @Query(
        value="SELECT COUNT(*) FROM $DATABASE_NAME WHERE $WORD_GEO_COL = ?1 LIMIT 1",
        nativeQuery=true,
    )
    fun isExactGeoWord(geoWord: String): Int

    @Query(
        value="SELECT COUNT(*) FROM $DATABASE_NAME WHERE $WORD_GEO_COL LIKE %?1%",
        nativeQuery=true,
    )
    fun countByGeoWordContains(subGeoWord: String): Long

    @Query(
        value="SELECT * FROM $DATABASE_NAME WHERE $WORD_GEO_COL LIKE %?1% ORDER BY $WORD_GEO_COL LIMIT ?2",
        nativeQuery=true,
    )
    fun findByGeoWordContains(subGeoWord: String, nLimit: Int): List<WordsEntity>
}
