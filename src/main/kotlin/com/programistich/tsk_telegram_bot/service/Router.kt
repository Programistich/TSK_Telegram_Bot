package com.programistich.tsk_telegram_bot.service

import com.programistich.tsk_telegram_bot.common.Extensions.id
import com.programistich.tsk_telegram_bot.common.Extensions.messageId
import com.programistich.tsk_telegram_bot.model.Chat
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.objects.Update

@Service
class Router(
    private val carService: CarService,
    private val chatService: ChatService,
    private val telegramService: TelegramService
) {

    fun registerChat(update: Update) {
        val chatId = update.id()
        val messageId = update.messageId()
        val notExistChat = chatService.notExistChat(chatId)
        if (notExistChat) {
            val chat = Chat()
            chat.chatId = chatId
            chat.cars = carService.getAllCars()
            chat.currentCar = chat.cars.split(", ")[0].toInt()
            chatService.save(chat)
        }
        telegramService.sendMessageWithKeyboard(chatId, "/get - просмотреть все машины")
        telegramService.deleteMessage(chatId, messageId)
    }

    fun getCatalogCars(update: Update) {
        val chatId = update.id()
        val messageId = update.message.messageId
        val chat = chatService.getChatById(chatId)
        val car = carService.getCarById(chat.currentCar)
        telegramService.sendCar(chatId, car)
        telegramService.deleteMessage(chatId, messageId)
    }

    fun updateNextCatalogCar(chatId: String, messageId: Int) {
        val chat = chatService.getChatById(chatId)
        val nextCar = getNextCar(chat.currentCar, chat.cars)
        chat.currentCar = nextCar
        val car = carService.getCarById(nextCar)
        chatService.save(chat)
        telegramService.updateCar(chatId, messageId, car)
    }

    fun updatePrevCatalogCar(chatId: String, messageId: Int) {
        val chat = chatService.getChatById(chatId)
        val prevCar = getPrevCar(chat.currentCar, chat.cars)
        chat.currentCar = prevCar
        val car = carService.getCarById(prevCar)
        chatService.save(chat)
        telegramService.updateCar(chatId, messageId, car)
    }

    fun getNextCar(currentCar: Int, cars: String): Int {
        val arrayCars = cars.split(", ").toList().map { it.toInt() }
        val index = arrayCars.indexOf(currentCar)
        val nextIndex = index + 1
        if (arrayCars.size > nextIndex) return arrayCars[nextIndex]
        return arrayCars[0]
    }

    fun getPrevCar(currentCar: Int, cars: String): Int {
        val arrayCars = cars.split(", ").toList().map { it.toInt() }
        val index = arrayCars.indexOf(currentCar)
        val prevIndex = index - 1
        if (prevIndex < 0) return arrayCars[arrayCars.lastIndex]
        return arrayCars[prevIndex]
    }

}