package hu.vanio.kotlin.feladat.ms.hu.vanio.kotlin.feladat.ms.client

import hu.vanio.kotlin.feladat.ms.hu.vanio.kotlin.feladat.ms.dto.Forecast
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

@Component
class WeatherClient(@Autowired val forecast: WebClient.RequestHeadersSpec<*>) {
    fun getForecast(): Forecast = forecast.retrieve().bodyToMono(Forecast::class.java).block()!!
}
