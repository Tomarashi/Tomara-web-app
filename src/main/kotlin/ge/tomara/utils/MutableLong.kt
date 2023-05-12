package ge.tomara.utils

class MutableLong(private var value: Long = 0) {

    fun getValue(): Long = value

    fun setValue(newValue: Long): MutableLong {
        value = newValue
        return this
    }

    fun increment(): Long = add(INCR_DECR_DIFF)

    fun add(diff: Long): Long {
        value += diff
        return value
    }

    fun decrement(): Long = subtraction(INCR_DECR_DIFF)

    fun subtraction(diff: Long): Long {
        value -= diff
        return value
    }

    override fun toString(): String = value.toString()

    companion object {
        private const val INCR_DECR_DIFF = 1.toLong()
    }

}
