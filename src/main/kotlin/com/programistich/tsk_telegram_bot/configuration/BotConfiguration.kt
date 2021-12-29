package com.programistich.tsk_telegram_bot.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "telegram")
data class BotConfiguration(
    var token: String = "",
    var username: String = "",
)