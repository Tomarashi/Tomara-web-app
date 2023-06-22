package ge.tomara.metrics.time

import java.util.Date
import java.util.SortedMap
import java.util.TreeMap

class DefaultTimeMetricsCollector: ITimeMetricsCollector {
    private val timePointsMap = TreeMap<Long, LocalMutInt>()

    private fun TreeMap<Long, LocalMutInt>.sumValues(): Int {
        return this.values.sumOf {
            it.value
        }
    }

    private fun SortedMap<Long, LocalMutInt>.sumValues(): Int {
        return this.values.sumOf {
            it.value
        }
    }

    override fun storeTimePoint() {
        storeTimePoint(Date())
    }

    override fun storeTimePoint(date: Date) {
        internalStore(date)
    }

    private fun internalStore(date: Date) {
        val time = date.time
        val value = timePointsMap[time]
        if(value == null) {
            timePointsMap[time] = LocalMutInt(1)
        } else {
            value.incr()
        }
    }

    override fun getMetricFrom(date: Date): Int {
        val time = date.time
        if(size() == 0) {
            return 0
        }
        return timePointsMap.tailMap(time).sumValues()
    }

    override fun getTimes(): List<Date> {
        return timePointsMap.keys.map { time ->
            Date(time)
        }
    }

    override fun size(): Int = timePointsMap.sumValues()

    override fun clearUntil(date: Date): Int {
        val map = timePointsMap.headMap(date.time)
        val size = map.sumValues()
        map.clear()
        return size
    }

    override fun clear() = timePointsMap.clear()

    override fun copy(): ITimeMetricsCollector {
        val collector = DefaultTimeMetricsCollector()
        collector.timePointsMap.putAll(timePointsMap.map {
            it.key to it.value.copy()
        })
        return collector
    }

    companion object {
        private class LocalMutInt(var value: Int = 0) {
            fun incr() {
                value += 1
            }

            fun copy(): LocalMutInt = LocalMutInt(value)

            override fun toString(): String = value.toString()
        }
    }
}
