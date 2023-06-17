package ge.tomara.response.words

import com.fasterxml.jackson.annotation.JsonProperty
import ge.tomara.entity.words.WordsEntity

data class WordResponse(
    @JsonProperty("word_id") var id: Int,
    @JsonProperty("word_geo") var wordGeo: String,
    @JsonProperty("type") var type: WordResponseType,
) {
    companion object {
        fun from(entity: WordsEntity, type: WordResponseType = WordResponseType.UNKNOWN): WordResponse {
            return WordResponse(
                entity.id,
                entity.wordGeo,
                type,
            )
        }

        fun from(id: Int, wordGeo: String, type: WordResponseType = WordResponseType.UNKNOWN): WordResponse {
            return WordResponse(id, wordGeo, type)
        }
    }
}
