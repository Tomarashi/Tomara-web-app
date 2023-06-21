package ge.tomara.Tomarawebapp.metrics

import ge.tomara.metrics.DefaultMetricsCollector
import ge.tomara.metrics.LimitedMetricsCollector
import ge.tomara.metrics.MetricsCollectorOffsetType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class TestMetricsCollector {

    @Test
    fun testDefaultMetricsCollector() {
        val initPoint = System.currentTimeMillis()
        val metricsCollector = DefaultMetricsCollector(initPoint)

        assertEquals(0, metricsCollector.getMetricFrom(initPoint - 1))
        assertEquals(0, metricsCollector.getMetricFrom(initPoint + 1))

        val testTimePoint = initPoint + 100
        metricsCollector.storeTimePoint(testTimePoint)
        assertEquals(1, metricsCollector.getMetricFrom(initPoint))

        metricsCollector.clear()
        assertEquals(0, metricsCollector.size())

        val end = 10
        val offset = 100
        val testTimeValues = mutableListOf<Long>().apply {
            for(i in 1..end) {
                add(initPoint + i * offset)
            }
        }

        testTimeValues.forEach { timeVal ->
            metricsCollector.storeTimePoint(timeVal)
        }

        assertEquals(end, metricsCollector.size())

        testTimeValues.forEachIndexed { i, timeVal ->
            assertEquals(
                testTimeValues.size - i - 1,
                metricsCollector.getMetricFrom(timeVal + 1),
            )
        }

        assertEquals(end, metricsCollector.getMetricFrom(initPoint))
        assertEquals(end, metricsCollector.getMetricFrom(initPoint - 1))
        assertEquals(end - 1, metricsCollector.getMetricFrom(initPoint + offset + 1))

        assertEquals(metricsCollector.size(), metricsCollector.getTimes().size)
        assertEquals(initPoint + offset, metricsCollector.getTimes().first().time)
    }

    @Test
    fun testDefaultMetricsCollectorOffset() {
        val millisInADay = 24 * 60 * 60 * 1000
        val initPoint = System.currentTimeMillis() - 7 * millisInADay
        val testTimePoint = initPoint + 1
        val metricsCollector = DefaultMetricsCollector(initPoint)

        for(i in 0 until 7) {
            metricsCollector.storeTimePoint(testTimePoint + i * millisInADay + 1000)
        }

        assertEquals(7, metricsCollector.getMetricOfOffset(MetricsCollectorOffsetType.WEEK))
    }

    @Test
    fun testLimitedMetricsCollector() {
        val metricsCollector = LimitedMetricsCollector()
        assertEquals(0, metricsCollector.size())

        for(i in 0 until LimitedMetricsCollector.METRICS_LIMIT / 2) {
            metricsCollector.storeTimePoint()
        }
        assertEquals(LimitedMetricsCollector.METRICS_LIMIT / 2, metricsCollector.size())

        metricsCollector.clear()
        assertEquals(0, metricsCollector.size())

        for(i in 0 until LimitedMetricsCollector.METRICS_LIMIT) {
            metricsCollector.storeTimePoint()
        }
        assertEquals(LimitedMetricsCollector.METRICS_LIMIT, metricsCollector.size())

        for(i in 0 until 100) {
            metricsCollector.storeTimePoint()
        }
        assertEquals(LimitedMetricsCollector.METRICS_LIMIT, metricsCollector.size())

        metricsCollector.setLimit(LimitedMetricsCollector.METRICS_LIMIT * 2)
        for(i in 0 until LimitedMetricsCollector.METRICS_LIMIT) {
            metricsCollector.storeTimePoint()
        }
        assertEquals(LimitedMetricsCollector.METRICS_LIMIT * 2, metricsCollector.size())

        val testLimit = LimitedMetricsCollector.METRICS_LIMIT / 100
        assertTrue(testLimit < metricsCollector.size())
        metricsCollector.setLimit(testLimit)
        assertEquals(testLimit, metricsCollector.size())

        assertThrows<IllegalArgumentException> {
            metricsCollector.setLimit(0)
        }
        assertThrows<IllegalArgumentException> {
            metricsCollector.setLimit(-1)
        }
    }

    @Test
    fun testMetricsCollectorOffsetType() {
        mapOf(
            MetricsCollectorOffsetType.HOUR to listOf("h", "H", "hour", "Hour"),
            MetricsCollectorOffsetType.DAY to listOf("d", "D", "day", "Day"),
            MetricsCollectorOffsetType.WEEK to listOf("w", "W", "week", "Week"),
        ).forEach { (t, values) ->
            for(value in values) {
                val parsed = MetricsCollectorOffsetType.parseCustom(value)
                assertEquals(t, parsed)
            }
        }
    }

}
