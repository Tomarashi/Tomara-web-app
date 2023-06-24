package ge.tomara.response.admin

import com.fasterxml.jackson.annotation.JsonProperty

data class OfferFrequencyResponseEntry<T>(
    @JsonProperty("offer_id") var offerId: Int,
    @JsonProperty("word") var word: T,
    @JsonProperty("frequency") var frequency: Int,
)

data class OfferFrequencyResponse<T>(
    @JsonProperty("result") var result: List<OfferFrequencyResponseEntry<T>>,
)
