package ge.tomara.response.admin

import com.fasterxml.jackson.annotation.JsonProperty

data class OfferAllCountResponse(
    @JsonProperty("offer_add_count") var offerAddCount: Int,
    @JsonProperty("offer_delete_count") var offerDeleteCount: Int,
)
