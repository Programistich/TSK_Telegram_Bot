package com.programistich.tsk_telegram_bot.parser

import com.programistich.tsk_telegram_bot.model.*
import com.programistich.tsk_telegram_bot.repository.CarRepository
import org.jsoup.Jsoup
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class ParserTSKService(
    private val carRepository: CarRepository
) {

    private val logger = LoggerFactory.getLogger(ParserTSKService::class.java)

    //@Scheduled(fixedDelay = 1000000000)
    fun startParseMainPage() {
        logger.info("Start parse tsk catalog")
        val page = Jsoup.connect("https://tsk.ua/katalog-avto/").userAgent("Mozilla").timeout(5000)
            .referrer("https://google.com").get()
        val cars = page.select("div.row.rowpad14.auto-clear > div.col-lg-6.col-md-6.col-sm-6.col-xs-12")
        cars.map {
            val linkOnCar = it.select("a").first()!!.attr("abs:href")
            logger.info("start parse link $linkOnCar")
            parseCarByLink(linkOnCar)
            Thread.sleep(100)
        }
        logger.info("End parse")
    }

    private fun parseCarByLink(linkOnCar: String) {
        val car = Car()
        val carPage = Jsoup.connect(linkOnCar).userAgent("Mozilla").timeout(5000).referrer("https://google.com").get()

        val name = carPage.select("h1.autotesla__item__h1").text()
        val status = carPage.select("div.autotesla__item__status").text()
        val price = carPage.select("div.autotesla__item__cena__end").text()
        val image = "https://tsk.ua" + carPage.select("a.owl-autotesla__item").attr("href")

        car.link = linkOnCar
        car.name = name
        car.status = convertStatus(status)
        car.price = convertPrice(price)
        car.image = image

        val details = carPage.select("div.autotesla__item__specif > div")
        details.map {
            var detailName = it.select("div").text()
            val detailValue = it.select("span").text()
            detailName.replace(detailValue, "")
            detailName = detailName.replace(detailValue, "").trimStart().trimEnd()
            when (detailName) {
                "Модель:" -> car.model = convertModel(detailValue)
                "Год выпуска:" -> car.year = convertYear(detailValue)
                "Пробег:" -> car.mileage = convertMileage(detailValue)
                "Привод:" -> car.driveUnit = convertDriveUnit(detailValue)
                "Подвеска:" -> car.suspension = convertSuspension(detailValue)
                "Запас хода:" -> car.range = detailValue
                "Город:" -> car.city = detailValue
                else -> {}
            }
        }
        carRepository.save(car)
        println(car)
    }

    private fun convertStatus(status: String): CarStatus? {
        return when (status) {
            "В наличии" -> CarStatus.IN_STOCK
            "Под заказ" -> CarStatus.ON_ORDER
            "Продано" -> CarStatus.SOLD
            "Ожидается" -> CarStatus.EXPECTED
            else -> null
        }
    }

    private fun convertPrice(price: String): Int {
        return price.replace("USD", "").replace(" ", "").toInt()
    }

    private fun convertModel(model: String?): CarModel? {
        return when (model) {
            "Model X" -> CarModel.MODEL_X
            "Model S" -> CarModel.MODEL_S
            "Model 3" -> CarModel.MODEL_3
            "Model Y" -> CarModel.MODEL_Y
            "Renault City K-ZE" -> CarModel.RENAULT_CITY_K_ZE
            else -> null
        }
    }

    private fun convertYear(year: String?): Int? {
        if (year == null) return null
        if (year.length == 2) return ("20$year").toInt()
        if (year.length == 4) return year.toInt()
        return null
    }

    private fun convertMileage(mileage: String?): Int? {
        if (mileage == null) return null
        return ((mileage.replace(" тыс.км", "")).toDouble() * 1000).toInt()
    }

    private fun convertDriveUnit(driveUnit: String?): CarDriveUnit? {
        return when (driveUnit) {
            "Полный" -> CarDriveUnit.FULL
            "Задний" -> CarDriveUnit.FRONT
            else -> null
        }
    }

    private fun convertSuspension(suspension: String?): CarSuspension? {
        return when (suspension) {
            "Пневматическая" -> CarSuspension.PNEUMATIC
            "Пружинная" -> CarSuspension.SPRING
            else -> null
        }
    }
}