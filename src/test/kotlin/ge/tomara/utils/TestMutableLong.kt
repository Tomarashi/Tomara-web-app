package ge.tomara.Tomarawebapp.utils

import ge.tomara.utils.MutableLong
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class TestMutableLong {

    @Test
    fun simpleTest() {
        val maxValue = 10
        val mutableLong = MutableLong()
        Assertions.assertEquals(0, mutableLong.getValue())

        for(i in 0 until maxValue) {
            mutableLong.increment()
        }
        Assertions.assertEquals(maxValue.toLong(), mutableLong.getValue())

        for(i in 0 until maxValue) {
            mutableLong.decrement()
        }
        Assertions.assertEquals(0, mutableLong.getValue())
    }

    @Test
    fun threadSafeTest() {
        val threadNum = 10
        val maxIteration = 100000
        val mutableLong = MutableLong()

        val threads = mutableListOf<Thread>()
        for(i in 0 until threadNum) {
            val t = Thread() {
                for(j in 0 until maxIteration) {
                    mutableLong.increment()
                }
            }
            threads.add(t)
            t.start()
        }

        for(t in threads) {
            t.join()
        }

        Assertions.assertEquals((threadNum * maxIteration).toLong(), mutableLong.getValue())
    }

}
