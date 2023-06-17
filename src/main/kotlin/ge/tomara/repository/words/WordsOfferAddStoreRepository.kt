package ge.tomara.repository.words

import ge.tomara.entity.words.WordsOfferAddStoreEntity
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface WordsOfferAddStoreRepository: CrudRepository<WordsOfferAddStoreEntity, Int> {
    companion object {
        const val DATABASE_NAME = "words_offer_add_store"
    }

    @Query(value="SELECT * FROM $DATABASE_NAME WHERE word_geo = ?1 LIMIT 1", nativeQuery=true)
    fun findByWordGeo(wordGeo: String): WordsOfferAddStoreEntity?

}
