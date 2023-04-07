package ge.tomara

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TomaraWebAppApplication

fun main(args: Array<String>) {
    runApplication<TomaraWebAppApplication>(*args)
}
