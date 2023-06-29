package ge.tomara.utils

import ge.tomara.utils.TypeUtils.Companion.parseBoolean
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class TestTypeUtils {
    @Test
    fun testParseBoolean() {
        assertTrue(parseBoolean("true"))
        assertTrue(parseBoolean("TRUE"))
        assertTrue(parseBoolean("True"))
        assertTrue(parseBoolean(Int.MAX_VALUE.toString()))
        assertTrue(parseBoolean(1.toString()))
        assertTrue(parseBoolean((-1).toString()))
        assertTrue(parseBoolean(Int.MIN_VALUE.toString()))

        assertFalse(parseBoolean("false"))
        assertFalse(parseBoolean("FALSE"))
        assertFalse(parseBoolean("False"))
        assertFalse(parseBoolean(0.toString()))

        assertThrows<NumberFormatException> {
            parseBoolean("1.1")
        }
        assertThrows<NumberFormatException> {
            parseBoolean("a")
        }
        assertThrows<NumberFormatException> {
            parseBoolean("a1")
        }
    }
}
