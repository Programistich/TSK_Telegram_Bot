package com.programistich.tsk_telegram_bot.common

import org.telegram.telegrambots.meta.api.objects.EntityType
import org.telegram.telegrambots.meta.api.objects.Update

object Extensions {

    fun Update.id(): String {
        return this.message.chatId.toString()
    }

    fun Update.messageId(): Int {
        return this.message.messageId
    }

    fun Update.getCommand(botName: String): Command? {
        val entities = this.message.entities ?: return null
        for (entity in entities) {
            if (entity.offset == 0 && entity.type == EntityType.BOTCOMMAND) {
                val parts = entity.text.split("@")
                if (parts.size == 1) {
                    return Command.LOOKUP[parts[0]]
                }
                if (parts[1] == botName) {
                    return Command.LOOKUP[parts[0]]
                }
            }
        }
        return null
    }
}