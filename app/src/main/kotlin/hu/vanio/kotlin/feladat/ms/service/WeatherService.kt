package hu.vanio.kotlin.feladat.ms.hu.vanio.kotlin.feladat.ms.service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import hu.vanio.kotlin.feladat.ms.hu.vanio.kotlin.feladat.ms.client.WeatherClient
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.io.OutputStream

@Service
class WeatherService(@Autowired val weatherClient: WeatherClient) {
    @PostConstruct
    fun postConstruct() {
        printDailyAverages()
    }

    fun printDailyAverages(out: OutputStream = System.out) = jacksonObjectMapper().writeValue(out, getDailyAverages())

    fun getDailyAverages() = getHourly().groupBy { it.first.toLocalDate() }
        .mapValues { it.value.map { pair -> pair.second }.average() }

    fun getHourly() = weatherClient.getForecast().hourly.let {
        it.time.zip(it.temperature_2m)
    }
}
