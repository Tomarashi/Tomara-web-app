package ge.tomara.metrics.time

import java.util.Date

interface ITimeMetricsCollector {
    fun storeTimePoint()
    fun storeTimePoint(date: Date)

    fun getMetricFrom(date: Date): Int

    fun getTimes(): List<Date>
    fun size(): Int

    fun clearUntil(date: Date): Int
    fun clear()

    fun copy(): ITimeMetricsCollector

}
