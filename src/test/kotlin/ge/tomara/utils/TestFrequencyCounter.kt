package ge.tomara.Tomarawebapp.utils

import ge.tomara.utils.FrequencyCounter
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class TestFrequencyCounter {

    @Test
    fun testIncrementAndDecrement() {
        val counter = FrequencyCounter<Char>()
        val testChar = 'a'
        val testChar2 = 'b'
        val testChar3 = 'c'

        counter.increment(testChar)
        Assertions.assertEquals(1, counter.get(testChar))

        counter.decrement(testChar)
        Assertions.assertEquals(0, counter.get(testChar))

        counter.increment(testChar).increment(4)
        Assertions.assertEquals(5, counter.get(testChar))

        counter.increment(testChar2, -10)
        counter.decrement(testChar3, 10)
        Assertions.assertEquals(counter.get(testChar2), counter.get(testChar3))
    }

    @Test
    fun testFilters() {
        val counter = FrequencyCounter<String>()

        val entries = mapOf(
            "a" to -1,
            "b" to 1,
            "c" to -10,
            "d" to 2,
            "e" to 5,
        )
        entries.forEach { (key, value) ->
            counter.put(key, value)
        }

        Assertions.assertEquals(entries.size, counter.size())

        val copiedCounter0 = counter.copy().filter { _, v ->
            v > Int.MIN_VALUE
        }
        Assertions.assertEquals(entries.size, copiedCounter0.size())

        val copiedCounter1 = counter.copy().filter { _, v ->
            v > Int.MAX_VALUE
        }
        Assertions.assertEquals(0, copiedCounter1.size())

        val copiedCounter2 = counter.copy().filterDefault()
        val filteredEntries = entries.filter { it.value > 0 }
        Assertions.assertEquals(filteredEntries.size, copiedCounter2.size())
    }

}
