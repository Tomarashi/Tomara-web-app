package ge.tomara.metrics

import java.util.LinkedList

open class LimitedMetricsCollector(
    private val initPoint: Long = System.currentTimeMillis()
): AbstractMetricsCollector() {
    private val timePoints = LinkedList<Int>()

    private var limit: Int = METRICS_LIMIT

    fun setLimit(newLimit: Int) {
        if(newLimit <= 0) {
            throw IllegalArgumentException("Invalid limit: $newLimit")
        }
        limit = newLimit
        dropFirstElementsIfNeeded(false)
    }

    private fun diffFromInitPoint(time: Long): Int = (time - initPoint).toInt()

    override fun storeTimePoint(time: Long) {
        dropFirstElementsIfNeeded()
        timePoints.add(diffFromInitPoint(time))
    }

    private fun dropFirstElementsIfNeeded(extraSpace: Boolean = true) {
        while(size() > limit) {
            timePoints.removeFirst()
        }
        if(extraSpace && size() == limit) {
            timePoints.removeFirst()
        }
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

    companion object {
        const val METRICS_LIMIT = 10000
    }
}
