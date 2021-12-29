package com.programistich.tsk_telegram_bot.service

import com.programistich.tsk_telegram_bot.model.Car
import com.programistich.tsk_telegram_bot.model.CarStatus
import com.programistich.tsk_telegram_bot.repository.CarRepository
import org.springframework.stereotype.Service

@Service
class CarService(
    private val carRepository: CarRepository
) {
    fun getAllCars(): String {
        return carRepository.findAll().filter {
            val cars = listOf(CarStatus.IN_STOCK, CarStatus.ON_ORDER, CarStatus.EXPECTED)
            cars.contains(it.status)
        }.map { it.id }.joinToString(", ")
    }

    fun getCarById(id: Int): Car {
        return carRepository.getById(id)
    }
}