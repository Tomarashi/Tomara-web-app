package ge.tomara.utils

class FrequencyCounter<T>() {
    companion object {
        class PostFrequencyCounter<T>(
            private val value: T,
            private val frequencyCounter: FrequencyCounter<T>,
        ) {
            fun increment(diffValue: Long = 1): PostFrequencyCounter<T> {
                frequencyCounter.increment(value, diffValue)
                return this
            }

            fun decrement(diffValue: Long = 1): PostFrequencyCounter<T> {
                frequencyCounter.decrement(value, diffValue)
                return this
            }
        }
    }

    private val frequency = LinkedHashMap<T, MutableLong>()

    constructor(initial: Map<T, MutableLong>) : this() {
        frequency.putAll(initial)
    }

    private fun checkValue(value: T): MutableLong {
        if(frequency[value] == null) {
            frequency[value] = MutableLong()
        }
        return frequency[value]!!
    }

    fun increment(value: T, diffValue: Long = 1): PostFrequencyCounter<T> {
        checkValue(value).add(diffValue)
        return PostFrequencyCounter(value, this)
    }

    fun decrement(value: T, diffValue: Long = 1): PostFrequencyCounter<T> {
        checkValue(value).subtraction(diffValue)
        return PostFrequencyCounter(value, this)
    }

    fun put(value: T, freq: Int): FrequencyCounter<T> {
        return put(value, freq.toLong())
    }

    fun put(value: T, freq: Long): FrequencyCounter<T> {
        frequency[value] = MutableLong(freq)
        return this
    }

    fun get(value: T): Long = frequency[value]?.getValue() ?: 0

    fun size(): Int = frequency.size

    fun filter(filterFn: (T, Long) -> Boolean): FrequencyCounter<T> {
        for(key in ArrayList(frequency.keys)) {
            if(!filterFn(key, frequency[key]!!.getValue())) {
                frequency.remove(key)
            }
        }
        return this
    }

    fun filterDefault(): FrequencyCounter<T> = filter { _, freq ->
        freq > 0
    }

    fun asMap(): Map<T, Long> = LinkedHashMap<T, Long>().apply {
        for((key, value) in frequency) {
            put(key, value.getValue())
        }
    }

    fun clear() {
        frequency.clear()
    }

    fun copy(): FrequencyCounter<T> {
        return FrequencyCounter(frequency)
    }

    override fun toString(): String = frequency.toString()

}
