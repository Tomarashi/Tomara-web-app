package ge.tomara.response.admin

import com.fasterxml.jackson.annotation.JsonProperty

data class OfferCountResponseTotalOrDistinct(
    @JsonProperty("total") var totalCount: Int,
    @JsonProperty("distinct") var distinctCount: Int,
)

data class OfferCountResponse(
    @JsonProperty("offer_add_count") var offerAddCount: OfferCountResponseTotalOrDistinct,
    @JsonProperty("offer_delete_count") var offerDeleteCount: OfferCountResponseTotalOrDistinct,
)
