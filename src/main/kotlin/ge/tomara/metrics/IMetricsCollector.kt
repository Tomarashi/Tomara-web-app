package ge.tomara.metrics

import java.util.Date

interface IMetricsCollector {
    fun storeTimePoint()
    fun storeTimePoint(date: Date)
    fun storeTimePoint(time: Long)

    fun getMetricFrom(date: Date): Int
    fun getMetricFrom(time: Long): Int
    fun getMetricOfOffset(offset: MetricsCollectorOffsetType): Int

    fun size(): Int
    fun clear()
}
