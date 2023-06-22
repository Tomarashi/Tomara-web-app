package ge.tomara.metrics.session

import java.util.Date

interface ISessionMetricsCollector {
    fun storeSession(sessionId: String)
    fun storeSession(sessionId: String, date: Date)

    fun getMetricFrom(date: Date): Int

    fun size(): Int

    fun clearUntil(date: Date): Int
    fun clear()

    fun copy(): ISessionMetricsCollector
}
