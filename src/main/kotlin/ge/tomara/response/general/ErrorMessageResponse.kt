package ge.tomara.response.general

import com.fasterxml.jackson.annotation.JsonValue

class ErrorMessageResponse(errorCode: Int, private val errorMsg: String) {
    private var errorCode: Int? = errorCode

    constructor(errorMsg: String): this(0, errorMsg) {
        errorCode = null
    }

    @JsonValue
    fun value(): Map<String, Any> {
        return HashMap<String, Any>().apply {
            put("error_msg", errorMsg)
            if(errorCode != null) {
                put("error_code", errorCode!!)
            }
        }
    }
}
