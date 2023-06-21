package ge.tomara.metrics

import java.util.Date

abstract class AbstractMetricsCollector: IMetricsCollector {
    override fun storeTimePoint() {
        storeTimePoint(getCurrentTime())
    }

    override fun storeTimePoint(date: Date) {
        storeTimePoint(date.time)
    }

    override fun getMetricFrom(date: Date): Int {
        return getMetricFrom(date.time)
    }

    override fun getMetricOfOffset(offset: MetricsCollectorOffsetType): Int {
        val realDate = getCurrentTime() - offset.timeInMillis
        return getMetricFrom(realDate)
    }

    protected open fun getCurrentTime(): Long = System.currentTimeMillis()

    protected open fun findIndexGreaterThan(sortedList: List<Int>, inputValue: Int): Int {
        var left = 0
        var right = sortedList.size - 1
        while (left <= right) {
            val mid = (left + right) / 2
            if (sortedList[mid] <= inputValue) {
                left = mid + 1
            } else {
                right = mid - 1
            }
        }
        return left
    }

}
