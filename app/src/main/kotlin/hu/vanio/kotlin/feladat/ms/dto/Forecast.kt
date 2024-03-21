package hu.vanio.kotlin.feladat.ms.hu.vanio.kotlin.feladat.ms.dto

import java.time.LocalDateTime

data class Forecast(
        var hourly: Hourly
)

data class Hourly (
        var time: Iterable<LocalDateTime>,
        var temperature_2m: Iterable<Double>
)
