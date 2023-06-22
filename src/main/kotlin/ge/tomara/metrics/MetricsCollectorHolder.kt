package ge.tomara.metrics

import ge.tomara.metrics.session.DefaultSessionMetricsCollector
import ge.tomara.metrics.session.ISessionMetricsCollector
import ge.tomara.metrics.session.SessionMetricsCollectorWrapper
import ge.tomara.metrics.time.DefaultTimeMetricsCollector
import ge.tomara.metrics.time.ITimeMetricsCollector
import ge.tomara.metrics.time.TimeMetricsCollectorWrapper

class MetricsCollectorHolder {
    companion object {
        private var TIME_METRICS: ITimeMetricsCollector? = null
        private var SESSION_METRICS: ISessionMetricsCollector? = null

        @JvmStatic
        fun getTimeMetrics(): ITimeMetricsCollector {
            if(TIME_METRICS == null) {
                TIME_METRICS = TimeMetricsCollectorWrapper(
                    DefaultTimeMetricsCollector()
                )
            }
            return TIME_METRICS!!
        }

        @JvmStatic
        fun getSessionMetrics(): ISessionMetricsCollector {
            if(SESSION_METRICS == null) {
                SESSION_METRICS = SessionMetricsCollectorWrapper(
                    DefaultSessionMetricsCollector()
                )
            }
            return SESSION_METRICS!!
        }
    }
}
