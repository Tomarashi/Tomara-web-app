package ge.tomara.metrics.time

import java.util.Date
import java.util.concurrent.locks.ReentrantLock

class TimeMetricsCollectorWrapper(
    private val wrapped: ITimeMetricsCollector
): ITimeMetricsCollector {
    private val lock = ReentrantLock()

    override fun storeTimePoint() {
        synchronized(lock) {
            wrapped.storeTimePoint()
        }
    }

    override fun storeTimePoint(date: Date) {
        synchronized(lock) {
            wrapped.storeTimePoint(date)
        }
    }

    override fun getMetricFrom(date: Date): Int {
        synchronized(lock) {
            return wrapped.getMetricFrom(date)
        }
    }

    override fun getTimes(): List<Date> {
        synchronized(lock) {
            return wrapped.getTimes()
        }
    }

    override fun size(): Int {
        synchronized(lock) {
            return wrapped.size()
        }
    }

    override fun clearUntil(date: Date): Int {
        synchronized(lock) {
            return wrapped.clearUntil(date)
        }
    }

    override fun clear() {
        synchronized(lock) {
            wrapped.clear()
        }
    }

    override fun copy(): ITimeMetricsCollector {
        synchronized(lock) {
            return wrapped.copy()
        }
    }
}
