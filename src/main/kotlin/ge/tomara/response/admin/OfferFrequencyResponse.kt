package ge.tomara.response.admin

import com.fasterxml.jackson.annotation.JsonProperty

data class OfferFrequencyResponse<T>(
    @JsonProperty("result") var result: Map<T, Int>,
)
