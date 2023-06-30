package ge.tomara.controllers

import ge.tomara.controllers.RequestURLUtils.Companion.isAdminPrefix
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

private typealias Case = Triple<String, Regex?, Boolean>

class TestRequestURLUtils {
    @Test
    fun testIsAdminPrefix() {
        listOf(
            Case("/", null, false),
            Case("/", Regex("/admin.*"), false),
            Case("/api", null, false),
            Case("/api", Regex("/admin.*"), false),
            Case("/admin", null, true),
            Case("/admin", Regex("/admin.*"), true),
            Case("/admin/query", null, true),
            Case("/admin/query", Regex("/admin.*"), true),
        ).forEach {(url, regex, expected) ->
            val result = if(regex == null) {
                isAdminPrefix(url)
            } else {
                isAdminPrefix(url, regex)
            }

            assertEquals(expected, result)
        }
    }
}
