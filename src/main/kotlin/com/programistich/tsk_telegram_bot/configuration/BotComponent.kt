package com.programistich.tsk_telegram_bot.configuration

import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import org.telegram.telegrambots.bots.DefaultBotOptions
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession

@Component
class BotComponent {

    @Bean
    fun botConfig() = DefaultBotOptions()

    @Bean
    fun telegramBot(bot: Bot): TelegramBotsApi {
        val telegramBotsApi = TelegramBotsApi(DefaultBotSession::class.java)
        telegramBotsApi.registerBot(bot)
        return telegramBotsApi
    }


}