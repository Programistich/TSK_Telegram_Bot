package com.programistich.tsk_telegram_bot.repository

import com.programistich.tsk_telegram_bot.model.Chat
import org.springframework.data.jpa.repository.JpaRepository

interface ChatRepository : JpaRepository<Chat, String>