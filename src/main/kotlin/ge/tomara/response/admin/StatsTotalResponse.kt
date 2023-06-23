package ge.tomara.response.admin

import com.fasterxml.jackson.annotation.JsonProperty

data class StatsTotalResponseViewsOrUniques(
    @JsonProperty("total") var total: Int,
    @JsonProperty("week") var week: Int,
    @JsonProperty("day") var day: Int,
)

data class StatsTotalResponse(
    @JsonProperty("views") var views: StatsTotalResponseViewsOrUniques,
    @JsonProperty("uniques") var uniques: StatsTotalResponseViewsOrUniques,
)
