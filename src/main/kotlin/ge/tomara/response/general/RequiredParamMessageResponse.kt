package ge.tomara.response.general

open class RequiredParamMessageResponse(paramName: String): ErrorMessageResponse(
    "Parameter '$paramName' is required"
)
