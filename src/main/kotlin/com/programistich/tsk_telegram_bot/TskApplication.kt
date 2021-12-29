package com.programistich.tsk_telegram_bot

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class TskApplication

fun main() {
    runApplication<TskApplication>()
}
