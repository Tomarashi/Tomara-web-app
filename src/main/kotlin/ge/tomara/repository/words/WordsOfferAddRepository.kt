package ge.tomara.repository.words

import ge.tomara.entity.words.WordsOfferAddEntity
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
interface WordsOfferAddRepository: CrudRepository<WordsOfferAddEntity, Int> {
    companion object {
        const val TABLE_NAME = "words_offer_add"

        private const val RESULT_LIMIT = 30
        private const val CONTROL_FIELD = "add_offer_word_id"
    }

    @Query(value="""
        SELECT $CONTROL_FIELD, COUNT(*) AS TOTAL_COUNT
        FROM $TABLE_NAME
        GROUP BY $CONTROL_FIELD
        ORDER BY TOTAL_COUNT DESC
        LIMIT $RESULT_LIMIT
    """, nativeQuery=true)
    fun getOfferAddIdFrequency(): List<List<Int>>

    @Query(value="""
        SELECT COUNT(*) AS TOTAL_COUNT
        FROM $TABLE_NAME
        WHERE $CONTROL_FIELD = :offerDeleteId
    """, nativeQuery=true)
    fun getOfferAddIdFrequency(@Param("offerDeleteId") offerWordId: Int): Int

    @Modifying
    @Query(value="""
        DELETE FROM $TABLE_NAME
        WHERE $CONTROL_FIELD = :offerAddId
    """, nativeQuery=true)
    fun deleteAllByOfferAddId(@Param("offerAddId") offerAddId: Int)

}
