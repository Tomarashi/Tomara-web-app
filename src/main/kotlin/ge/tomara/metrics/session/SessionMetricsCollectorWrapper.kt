package ge.tomara.metrics.session

import java.util.Date
import java.util.concurrent.locks.ReentrantLock

class SessionMetricsCollectorWrapper(
    private val wrapped: ISessionMetricsCollector
): ISessionMetricsCollector {
    private val lock = ReentrantLock()

    override fun storeSession(sessionId: String) {
        synchronized(lock) {
            wrapped.storeSession(sessionId)
        }
    }

    override fun storeSession(sessionId: String, date: Date) {
        synchronized(lock) {
            wrapped.storeSession(sessionId, date)
        }
    }

    override fun getMetricFrom(date: Date): Int {
        synchronized(lock) {
            return wrapped.getMetricFrom(date)
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

    override fun copy(): ISessionMetricsCollector {
        synchronized(lock) {
            return wrapped.copy()
        }
    }
}
