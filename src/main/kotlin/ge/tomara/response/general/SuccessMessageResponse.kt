package ge.tomara.response.general

import com.fasterxml.jackson.annotation.JsonValue

open class SuccessMessageResponse(successCode: Int, private val successMsg: String): IMessageResponse {
    private var successCode: Int? = successCode

    constructor(successMsg: String): this(0, successMsg) {
        successCode = null
    }

    @JsonValue
    override fun value(): Map<String, Any> {
        return HashMap<String, Any>().apply {
            put("success_msg", successMsg)
            if(successCode != null) {
                put("success_code", successCode!!)
            }
        }
    }
}
