package ge.tomara.repository.words

import ge.tomara.entity.words.WordsOfferDeleteEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface WordsOfferDeleteRepository: CrudRepository<WordsOfferDeleteEntity, Int> {
    companion object {
        const val DATABASE_NAME = "words_offer_delete"
    }
}
