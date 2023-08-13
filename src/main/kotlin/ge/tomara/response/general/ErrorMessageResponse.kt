package ge.tomara.response.general

import com.fasterxml.jackson.annotation.JsonValue

open class ErrorMessageResponse(errorCode: Int, private val errorMsg: String): IMessageResponse {
    private var errorCode: Int? = errorCode

    constructor(errorMsg: String): this(0, errorMsg) {
        errorCode = null
    }

    @JsonValue
    override fun value(): Map<String, Any> {
        return HashMap<String, Any>().apply {
            put("error_msg", errorMsg)
            if(errorCode != null) {
                put("error_code", errorCode!!)
            }
        }
    }
}
