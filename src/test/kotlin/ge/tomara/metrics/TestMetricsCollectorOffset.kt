package ge.tomara.metrics

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.Date

class TestMetricsCollectorOffset {
    @Test
    fun testMetricsCollectorOffsetType() {
        mapOf(
            MetricsCollectorOffset.HOUR to listOf("h", "H", "hour", "Hour"),
            MetricsCollectorOffset.DAY to listOf("d", "D", "day", "Day"),
            MetricsCollectorOffset.WEEK to listOf("w", "W", "week", "Week"),
        ).forEach { (t, values) ->
            for(value in values) {
                val parsed = MetricsCollectorOffset.parseCustom(value)
                assertEquals(t, parsed)
            }
        }
    }

    @Test
    fun testMetricsCollectorOffsetTypeFrom() {
        val date = Date()

        val dateHour = MetricsCollectorOffset.HOUR.offsetFrom(date)
        assertEquals(date.time - 1000 * 60 * 60, dateHour.time)
    }

}