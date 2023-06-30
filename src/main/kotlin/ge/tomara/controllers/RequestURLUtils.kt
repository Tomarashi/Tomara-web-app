package ge.tomara.controllers

class RequestURLUtils {
    companion object {
        val ADMIN_PATH_REGEX = Regex("/admin.*")

        @JvmStatic
        fun isAdminPrefix(url: String, prefix: Regex = ADMIN_PATH_REGEX): Boolean {
            return prefix.matches(url)
        }
    }
}
