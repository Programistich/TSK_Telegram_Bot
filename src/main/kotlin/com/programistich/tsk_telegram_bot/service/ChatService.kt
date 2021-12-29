package com.programistich.tsk_telegram_bot.service

import com.programistich.tsk_telegram_bot.model.Chat
import com.programistich.tsk_telegram_bot.repository.ChatRepository
import org.springframework.stereotype.Service

@Service
class ChatService(
    private val chatRepository: ChatRepository
) {
    fun save(chat: Chat) {
        chatRepository.save(chat)
    }

    fun notExistChat(chatId: String): Boolean {
        return chatRepository.findById(chatId).isEmpty
    }

    fun getChatById(chatId: String): Chat {
        return chatRepository.getById(chatId)
    }
}