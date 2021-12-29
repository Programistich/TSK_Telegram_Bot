package com.programistich.tsk_telegram_bot.service

import com.programistich.tsk_telegram_bot.configuration.Bot
import com.programistich.tsk_telegram_bot.model.Car
import com.programistich.tsk_telegram_bot.model.CarModel
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageMedia
import org.telegram.telegrambots.meta.api.objects.InputFile
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow
import org.telegram.telegrambots.meta.exceptions.TelegramApiException


@Service
class TelegramService(
    private val bot: Bot
) {

    fun sendMessage(chatId: String, text: String) {
        bot.execute(SendMessage(chatId, text))
    }

    fun sendMessageWithKeyboard(chatId: String, text: String) {
        val message = SendMessage()
        message.chatId = chatId
        message.text = text
        // message.replyMarkup = createReplyKeyboardMarkup()
        bot.execute(message)
    }

    fun deleteMessage(chatId: String, messageId: Int) {
        try {
            val delete = DeleteMessage()
            delete.chatId = chatId
            delete.messageId = messageId
            bot.execute(delete)
        } catch (_: TelegramApiException) {
        }
    }

    fun sendCar(chatId: String, car: Car) {
        val message = SendPhoto()
        message.replyMarkup = createInlineKeyboardMarkup()
        message.chatId = chatId
        message.photo = InputFile(car.image)
        message.caption = car.toString()
        message.parseMode = "html"
        bot.execute(message)
    }

    fun updateCar(chatId: String, messageId: Int, car: Car) {
        val inputMedia = InputMediaPhoto()
        inputMedia.media = car.image
        inputMedia.caption = car.toString()
        inputMedia.parseMode = "html"

        val message = EditMessageMedia()
        message.chatId = chatId
        message.messageId = messageId
        message.media = inputMedia
        message.replyMarkup = createInlineKeyboardMarkup()
        bot.execute(message)
    }

    fun createInlineKeyboardMarkup(): InlineKeyboardMarkup {
        val markupInline = InlineKeyboardMarkup()
        val rowsInline: MutableList<List<InlineKeyboardButton>> = ArrayList()
        val rowInline: MutableList<InlineKeyboardButton> = ArrayList()

        val inlineBack = InlineKeyboardButton()
        inlineBack.text = "⏮"
        inlineBack.callbackData = "prev"
        rowInline.add(inlineBack)

        val inlineNext = InlineKeyboardButton()
        inlineNext.text = "⏭"
        inlineNext.callbackData = "next"
        rowInline.add(inlineNext)

        rowsInline.add(rowInline)
        markupInline.keyboard = rowsInline
        return markupInline
    }

    fun createReplyKeyboardMarkup(): ReplyKeyboardMarkup {
        val keyboardMarkup = ReplyKeyboardMarkup()
        val keyboard: MutableList<KeyboardRow> = ArrayList()
        CarModel.values().map {
            val row = KeyboardRow()
            row.add(it.carModel)
            keyboard.add(row)
        }
        keyboardMarkup.keyboard = keyboard
        return keyboardMarkup
    }


}