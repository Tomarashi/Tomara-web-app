package ge.tomara.response

import com.fasterxml.jackson.annotation.JsonProperty
import ge.tomara.repository.entity.WordsEntity

data class WordFullResponse(
    @JsonProperty("word_id") var id: Int,
    @JsonProperty("word_geo") var wordGeo: String,
    @JsonProperty("word_eng") var wordEng: String,
    @JsonProperty("frequency") var frequency: Int,
    @JsonProperty("type") var type: WordResponseType,
) {
    companion object {
        fun from(entity: WordsEntity, type: WordResponseType = WordResponseType.UNKNOWN): WordFullResponse {
            return WordFullResponse(
                entity.id,
                entity.wordGeo,
                entity.wordEng,
                entity.frequency,
                type,
            )
        }

        fun from(
            id: Int, wordGeo: String, wordEng: String, frequency: Int,
            type: WordResponseType = WordResponseType.UNKNOWN,
        ): WordFullResponse {
            return WordFullResponse(id, wordGeo, wordEng, frequency, type)
        }
    }
}
