package ge.tomara.metrics

import java.util.Date

class DefaultMetricsCollector(
    private val initPoint: Long = System.currentTimeMillis()
): AbstractMetricsCollector() {
    private val timePoints = mutableListOf<Int>()

    private fun diffFromInitPoint(time: Long): Int = (time - initPoint).toInt()

    override fun storeTimePoint(time: Long) {
        timePoints.add(diffFromInitPoint(time))
    }

    override fun getMetricFrom(time: Long): Int {
        if(size() == 0) {
            return 0
        } else if(time < initPoint) {
            return size()
        }
        val index = findIndexGreaterThan(timePoints, diffFromInitPoint(time))
        return size() - index
    }

    override fun size(): Int = timePoints.size

    override fun clear() = timePoints.clear()

    fun getTimes(): List<Date> {
        return timePoints.map { p ->
            Date(initPoint + p)
        }
    }
}
