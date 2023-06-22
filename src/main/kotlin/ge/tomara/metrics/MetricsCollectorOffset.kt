package ge.tomara.metrics

import java.util.Date

private const val MILLIS_IN_HOUR = 60 * 60 * 1000
private const val MILLIS_IN_DAY = 24 * MILLIS_IN_HOUR
private const val MILLIS_IN_WEEK = 7 * MILLIS_IN_DAY

enum class MetricsCollectorOffset(val timeInMillis: Int) {
    HOUR(MILLIS_IN_HOUR),
    DAY(MILLIS_IN_DAY),
    WEEK(MILLIS_IN_WEEK);

    fun offsetFrom(date: Date): Date {
        return Date(date.time - timeInMillis)
    }

    companion object {
        @JvmStatic
        private fun equalsIgnoreCase(s0: String, s1: String): Boolean {
            return s0.equals(s1, ignoreCase=true)
        }

        @JvmStatic
        fun parseCustom(value: String): MetricsCollectorOffset {
            if(equalsIgnoreCase(value, "h")
            || equalsIgnoreCase(value, "hour")) {
                return HOUR
            } else if(equalsIgnoreCase(value, "d")
                    || equalsIgnoreCase(value, "day")) {
                return DAY
            } else if(equalsIgnoreCase(value, "w")
                    || equalsIgnoreCase(value, "week")) {
                return WEEK
            }
            throw IllegalArgumentException("Unknown type value: $value")
        }
    }
}
