package ge.tomara.response.words

import com.fasterxml.jackson.annotation.JsonValue

enum class WordResponseType(@JsonValue val typeIndex: Int, val typeName: String) {
    UNKNOWN(0, "Unknown"),
    VALID(1, "Valid"),
    DELETED(2, "Deleted");

    fun asMap(): Map<Int, String> {
        return values().associate { type -> type.typeIndex to type.typeName }
    }
}
