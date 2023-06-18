package ge.tomara.utils

import java.util.concurrent.locks.ReentrantLock

class MutableLong(private var value: Long = 0) {

    private val lock = ReentrantLock()

    fun getValue(): Long {
        synchronized(lock) {
            return value
        }
    }

    fun setValue(newValue: Long): MutableLong {
        synchronized(lock) {
            value = newValue
            return this
        }
    }

    fun increment(): Long = add(INCR_DECR_DIFF)

    fun add(diff: Long): Long {
        synchronized(lock) {
            value += diff
            return value
        }
    }

    fun decrement(): Long = subtraction(INCR_DECR_DIFF)

    fun subtraction(diff: Long): Long {
        synchronized(lock) {
            value -= diff
            return value
        }
    }

    override fun toString(): String {
        synchronized(lock) {
            return value.toString()
        }
    }

    companion object {
        private const val INCR_DECR_DIFF = 1.toLong()
    }

}
