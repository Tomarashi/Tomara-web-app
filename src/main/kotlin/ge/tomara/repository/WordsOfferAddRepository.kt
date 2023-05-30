package ge.tomara.repository

import ge.tomara.entity.WordsOfferAddEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface WordsOfferAddRepository: CrudRepository<WordsOfferAddEntity, Int> {
    companion object {
        const val DATABASE_NAME = "words_offer_add"
    }
}
