package com.programistich.tsk_telegram_bot.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "chats")
data class Chat(
    @Id
    @Column(name = "chat_id")
    var chatId: String = "",
    @Column(name = "chat_current_car")
    var currentCar: Int = 0,
    @Column(name = "chat_cars")
    var cars: String = "",
    @Column(name = "chat_message_id")
    val messageId: Int = 0,
)