package ge.tomara.utils

import kotlin.jvm.Throws

class TypeUtils {
    companion object {
        @JvmStatic
        @Throws(NumberFormatException::class)
        fun parseBoolean(value: String): Boolean {
            return if(value.equals("true", ignoreCase=true)) {
                true
            } else if(value.equals("false", ignoreCase=true)) {
                false
            } else {
                value.toLong() != 0.toLong()
            }
        }
    }
}
