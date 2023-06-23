package ge.tomara.repository.words

import ge.tomara.entity.words.WordsOfferDeleteEntity
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface WordsOfferDeleteRepository: CrudRepository<WordsOfferDeleteEntity, Int> {
    companion object {
        const val DATABASE_NAME = "words_offer_delete"

        private const val RESULT_LIMIT = 30
        private const val CONTROL_FIELD = "del_offer_word_id"
    }

    @Query(value="""
        SELECT $CONTROL_FIELD, COUNT(*) AS TOTAL_COUNT
        FROM $DATABASE_NAME
        GROUP BY $CONTROL_FIELD
        ORDER BY TOTAL_COUNT DESC
        LIMIT $RESULT_LIMIT
    """, nativeQuery=true)
    fun getOfferDeleteIdFrequency(): List<List<Int>>

    @Query(value="""
        SELECT COUNT(DISTINCT $CONTROL_FIELD)
        FROM $DATABASE_NAME
    """, nativeQuery=true)
    fun getOfferDeleteIdDistinctCount(): Long

}
