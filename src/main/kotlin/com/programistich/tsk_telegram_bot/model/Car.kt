package com.programistich.tsk_telegram_bot.model

import javax.persistence.*


@Entity
@Table(name = "cars")
data class Car(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_id")
    var id: Int = 0,
    @Column(name = "car_link")
    var link: String = "",
    @Column(name = "car_name")
    var name: String = "",
    @Column(name = "car_status")
    @Enumerated(EnumType.STRING)
    var status: CarStatus? = null,
    @Column(name = "car_price")
    var price: Int = 0,
    @Column(name = "car_image")
    var image: String = "",
    @Column(name = "car_model")
    @Enumerated(EnumType.STRING)
    var model: CarModel? = null,
    @Column(name = "car_year")
    var year: Int? = 0,
    @Column(name = "car_mileage")
    var mileage: Int? = 0,
    @Column(name = "car_drive_unit")
    @Enumerated(EnumType.STRING)
    var driveUnit: CarDriveUnit? = null,
    @Column(name = "car_suspension")
    @Enumerated(EnumType.STRING)
    var suspension: CarSuspension? = null,
    @Column(name = "car_range")
    var range: String? = "",
    @Column(name = "car_city")
    var city: String? = ""
) {
    override fun toString(): String {
        var result = ""
        result += "<b>$name</b>\n"
        result += "<a href=\"$link\">Линк</a>\n"
        if (status != null) result += "Статус: ${status.toString()}\n"
        result += "Цена: $price$\n"
        if (model != null) result += "Модель: ${model.toString()}\n"
        if (year != null) result += "Год: $year\n"
        if (mileage != null) result += "Пробег: ${mileage.toString()} км\n"
        if (driveUnit != null) result += "Привод: ${driveUnit.toString()}\n"
        if (range != null) result += "Запас Хода: ${range.toString()}\n"
        if (city != null) result += "Город: ${city.toString()}\n"
        return result
    }
}

enum class CarStatus(var carStatus: String) {
    IN_STOCK("В наличии"),
    ON_ORDER("Под заказ"),
    SOLD("Продано"),
    EXPECTED("Ожидается");

    override fun toString(): String {
        return carStatus
    }
}

enum class CarModel(var carModel: String) {
    MODEL_S("Model S"),
    MODEL_X("Model X"),
    MODEL_3("Model 3"),
    MODEL_Y("Model Y"),
    RENAULT_CITY_K_ZE("Renault City K-ZE");

    override fun toString(): String {
        return carModel
    }
}

enum class CarDriveUnit(var carDriveUnit: String) {
    FULL("Полный"),
    FRONT("Передний");

    override fun toString(): String {
        return carDriveUnit.lowercase()
    }
}

enum class CarSuspension(var carSuspension: String) {
    PNEUMATIC("Пневматическая"),
    SPRING("Пружинная");

    override fun toString(): String {
        return carSuspension.lowercase()
    }
}