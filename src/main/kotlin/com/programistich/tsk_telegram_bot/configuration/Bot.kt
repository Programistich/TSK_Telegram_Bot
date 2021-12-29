package com.programistich.tsk_telegram_bot.configuration

import com.programistich.tsk_telegram_bot.common.Command
import com.programistich.tsk_telegram_bot.common.Extensions.getCommand
import com.programistich.tsk_telegram_bot.service.Router
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Component
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.objects.Update

@Component
class Bot(
    private val telegramBotConfiguration: BotConfiguration,
    @Lazy private val router: Router
) : TelegramLongPollingBot() {

    override fun getBotToken(): String {
        return telegramBotConfiguration.token
    }

    override fun getBotUsername(): String {
        return telegramBotConfiguration.username
    }

    override fun onUpdateReceived(update: Update) {
        println(update)
        if (update.hasCallbackQuery()) {
            val callData = update.callbackQuery.data
            val messageId = update.callbackQuery.message.messageId.toInt()
            val chatId = update.callbackQuery.message.chatId.toString()
            if (callData.equals("next")) router.updateNextCatalogCar(chatId, messageId)
            if (callData.equals("prev")) router.updatePrevCatalogCar(chatId, messageId)
        } else if (update.getCommand(telegramBotConfiguration.username) == Command.START) {
            router.registerChat(update)
        } else if (update.getCommand(telegramBotConfiguration.username) == Command.GET) {
            router.getCatalogCars(update)
        }

    }
}