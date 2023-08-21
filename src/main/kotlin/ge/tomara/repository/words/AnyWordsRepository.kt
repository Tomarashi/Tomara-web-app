package ge.tomara.repository.words

import ge.tomara.entity.words.AnyWordsEntity
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

@Deprecated("Deprecated due to low performance in some cases")
interface AnyWordsRepository: CrudRepository<AnyWordsEntity, Int> {
    /**
     * Repository for union of 'tomara.words' and 'tomara.words_deleted'
     */

    @Query(value="""
        SELECT word_id AS id,
               word_geo as word_geo,
               ${AnyWordsEntity.WORD_VALID} as word_type
          FROM ${WordsRepository.DATABASE_NAME}
         WHERE word_eng LIKE %?1%
        UNION ALL
        SELECT del_word_id as id,
               del_word_geo as word_geo,
               ${AnyWordsEntity.WORD_DELETED} as word_type
          FROM ${WordsDeletedRepository.DATABASE_NAME}
         WHERE del_word_eng LIKE %?1%
         ORDER BY word_geo
         LIMIT ?2
    """, nativeQuery=true)
    fun findByGeoWordContains(subGeoWord: String, nLimit: Int): List<AnyWordsEntity>

}