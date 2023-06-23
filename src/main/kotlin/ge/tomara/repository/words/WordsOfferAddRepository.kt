package ge.tomara.repository.words

import ge.tomara.entity.words.WordsOfferAddEntity
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface WordsOfferAddRepository: CrudRepository<WordsOfferAddEntity, Int> {
    companion object {
        const val DATABASE_NAME = "words_offer_add"

        private const val RESULT_LIMIT = 30
        private const val GROUP_BY_FIELD = "add_offer_word_id"
    }

    @Query(value="""
        SELECT $GROUP_BY_FIELD, COUNT(*) AS TOTAL_COUNT
        FROM $DATABASE_NAME
        GROUP BY $GROUP_BY_FIELD
        ORDER BY TOTAL_COUNT DESC
        LIMIT $RESULT_LIMIT
    """, nativeQuery=true)
    fun getOfferAddIdFrequency(): List<List<Int>>

}
