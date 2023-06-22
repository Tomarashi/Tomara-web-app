package ge.tomara.Tomarawebapp.metrics

import ge.tomara.metrics.time.DefaultTimeMetricsCollector
import ge.tomara.metrics.MetricsCollectorOffset
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.Date

class TestTimeMetricsCollector {
    private fun Long.toDate(): Date = Date(this)

    private fun DefaultTimeMetricsCollector.getMetricFrom(time: Long): Int {
        return this.getMetricFrom(time.toDate())
    }

    private fun DefaultTimeMetricsCollector.storeTimePoint(time: Long) {
        this.storeTimePoint(time.toDate())
    }

    @Test
    fun testDefaultMetricsCollector() {
        val initPoint = System.currentTimeMillis()
        val metricsCollector = DefaultTimeMetricsCollector()

        assertEquals(0, metricsCollector.getMetricFrom(initPoint - 1))
        assertEquals(0, metricsCollector.getMetricFrom(initPoint + 1))

        val testTimePoint = initPoint + 100
        metricsCollector.storeTimePoint(testTimePoint)
        assertEquals(1, metricsCollector.getMetricFrom(initPoint))
        assertEquals(1, metricsCollector.getMetricFrom(testTimePoint))

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
    }

    @Test
    fun testDefaultMetricsCollectorClearFrom() {
        val initPoint = System.currentTimeMillis()
        val metricsCollector = DefaultTimeMetricsCollector()

        assertEquals(0, metricsCollector.size())

        mutableListOf<Date>().apply {
            val testEndPoint = initPoint + MetricsCollectorOffset.DAY.timeInMillis
            for(i in 0 until 10) {
                add(Date(testEndPoint - i - 1))
            }
        }.sorted().forEach { date ->
            metricsCollector.getMetricFrom(date)
        }

        metricsCollector.clearUntil(MetricsCollectorOffset.WEEK.offsetFrom(initPoint.toDate()))
        assertEquals(0, metricsCollector.size())

        metricsCollector.storeTimePoint(initPoint - 2000)
        metricsCollector.storeTimePoint(initPoint - 1000)
        metricsCollector.storeTimePoint(initPoint)
        metricsCollector.storeTimePoint(initPoint + 1000)
        metricsCollector.storeTimePoint(initPoint + 2000)

        assertEquals(3, metricsCollector.getMetricFrom(initPoint))
        assertEquals(2, metricsCollector.clearUntil(initPoint.toDate()))
    }

    @Test
    fun testDefaultMetricsCollectorOffset() {
        val millisInADay = 24 * 60 * 60 * 1000
        val initPoint = System.currentTimeMillis() - 7 * millisInADay
        val testTimePoint = initPoint + 1
        val metricsCollector = DefaultTimeMetricsCollector()

        for(i in 0 until 7) {
            metricsCollector.storeTimePoint(testTimePoint + i * millisInADay + 1000)
        }

        val fromDate = MetricsCollectorOffset.WEEK.offsetFrom(initPoint.toDate())
        assertEquals(7, metricsCollector.getMetricFrom(fromDate))
    }

    @Test
    fun testDefaultMetricsCollectorGetTimes() {
        val initPoint = System.currentTimeMillis()
        val metricsCollector = DefaultTimeMetricsCollector()
        val dates = mutableListOf<Date>().apply {
            for(i in 1 until 10) {
                add(Date(initPoint + i * 60 * 1000))
            }
        }

        dates.forEach { date ->
            metricsCollector.storeTimePoint(date)
        }

        assertEquals(dates, metricsCollector.getTimes())

        val metricsCollectorCopied = metricsCollector.copy() as DefaultTimeMetricsCollector
        assertEquals(metricsCollector.getTimes(), metricsCollectorCopied.getTimes())
    }

}
