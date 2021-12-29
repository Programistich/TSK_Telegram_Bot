package com.programistich.tsk_telegram_bot.repository

import com.programistich.tsk_telegram_bot.model.Car
import org.springframework.data.jpa.repository.JpaRepository

interface CarRepository : JpaRepository<Car, Int>