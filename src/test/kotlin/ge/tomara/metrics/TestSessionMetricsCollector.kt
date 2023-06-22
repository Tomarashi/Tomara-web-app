package ge.tomara.metrics

import ge.tomara.metrics.session.DefaultSessionMetricsCollector
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.Date
import kotlin.random.Random

class TestSessionMetricsCollector {
    private fun Long.toDate(): Date = Date(this)

    @Test
    fun testDefaultSessionMetricsCollector() {
        val collector = DefaultSessionMetricsCollector()
        val initDate = System.currentTimeMillis()
        val size = 10
        val sessionIds = generateSessionIds(size)

        sessionIds.forEachIndexed { i, s ->
            collector.storeSession(s, (initDate + i).toDate())
        }

        assertEquals(size, collector.getMetricFrom(initDate.toDate()))
        assertEquals(0, collector.getMetricFrom((initDate + size).toDate()))
        assertEquals(size - 5, collector.getMetricFrom((initDate + 5).toDate()))

        assertEquals(0, collector.clearUntil(initDate.toDate()))
        assertEquals(
            size, collector.copy().clearUntil((initDate + size).toDate()),
        )
        assertEquals(1, collector.copy().clearUntil((initDate + 1).toDate()))
        assertEquals(5, collector.copy().clearUntil((initDate + 5).toDate()))
    }

    companion object {
        private const val VALID_CHARS = "0123456789abcdef"

        @JvmStatic
        private fun generateSessionId(): String {
            return (0 until 16).map {
                VALID_CHARS[Random.nextInt(0, VALID_CHARS.length)]
            }.joinToString("")
        }

        @JvmStatic
        private fun generateSessionIds(n: Int = 8): Set<String> {
            return (0 until n).map {
                generateSessionId()
            }.toSet()
        }
    }
}
