package com.alex.eyk.telegram

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TelegramSpringApplication

fun main(args: Array<String>) {
    runApplication<TelegramSpringApplication>(*args)
}
