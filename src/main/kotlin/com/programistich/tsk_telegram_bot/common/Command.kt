package com.programistich.tsk_telegram_bot.common

enum class Command(val command: String) {
    START("/start"),
    GET("/get");

    companion object {
        val LOOKUP = values().associateBy(Command::command)
    }
}