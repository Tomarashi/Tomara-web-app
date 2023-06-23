package ge.tomara.response.admin

import com.fasterxml.jackson.annotation.JsonProperty

data class ValidCountResponse(
    @JsonProperty("add_count") var offerAddCount: Int,
    @JsonProperty("delete_count") var offerDeleteCount: Int,
)
