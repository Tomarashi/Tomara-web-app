package ge.tomara.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.locks.ReentrantLock
import javax.servlet.http.HttpSessionEvent
import javax.servlet.http.HttpSessionListener

@Configuration
class HttpSessionListenerWrapper {
    companion object {
        private val uniqueSessions = HashSet<String>()
        private val lock = ReentrantLock()

        @JvmStatic
        fun getActiveSessionsSize(): Int {
            synchronized(lock) {
                return uniqueSessions.size
            }
        }
    }

    @Bean
    fun httpSessionListener(): HttpSessionListener {
        return object : HttpSessionListener {
            override fun sessionCreated(hse: HttpSessionEvent) {
                synchronized(lock) {
                    uniqueSessions.add(hse.session.id)
                }
            }

            override fun sessionDestroyed(hse: HttpSessionEvent) {
                synchronized(lock) {
                    uniqueSessions.remove(hse.session.id)
                }
            }
        }
    }
}
