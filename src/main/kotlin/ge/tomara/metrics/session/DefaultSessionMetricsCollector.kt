package ge.tomara.metrics.session

import java.util.Date
import java.util.TreeMap

class DefaultSessionMetricsCollector: ISessionMetricsCollector {
    private val uniqueSessionIds = HashSet<String>()
    private val timeSessionsMap = TreeMap<Long, MutableSet<String>>()

    override fun storeSession(sessionId: String) {
        storeSession(sessionId, Date())
    }

    override fun storeSession(sessionId: String, date: Date) {
        if(!uniqueSessionIds.contains(sessionId)) {
            val time = date.time

            uniqueSessionIds.add(sessionId)
            val sessions = timeSessionsMap[time]
            if(sessions == null) {
                timeSessionsMap[time] = mutableSetOf(sessionId)
            } else {
                sessions.add(sessionId)
            }
        }
    }

    override fun getMetricFrom(date: Date): Int {
        return timeSessionsMap.tailMap(date.time).values.sumOf {
            it.size
        }
    }

    override fun size(): Int = uniqueSessionIds.size

    override fun clearUntil(date: Date): Int {
        val prev = size()
        val map = timeSessionsMap.headMap(date.time)
        map.values.forEach { values ->
            uniqueSessionIds.removeAll(values)
        }
        map.clear()
        return prev - size()
    }

    override fun clear() {
        uniqueSessionIds.clear()
        timeSessionsMap.clear()
    }

    override fun copy(): ISessionMetricsCollector {
        val collector = DefaultSessionMetricsCollector()
        collector.uniqueSessionIds.addAll(uniqueSessionIds)
        collector.timeSessionsMap.putAll(timeSessionsMap.map {
            it.key to HashSet(it.value)
        }.toMap())
        return collector
    }
}
