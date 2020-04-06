package me.jakub.risc

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RiscApplication

fun main(args: Array<String>) {
    runApplication<RiscApplication>(*args)
}