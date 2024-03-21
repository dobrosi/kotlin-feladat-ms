package hu.vanio.kotlin.feladat.ms.hu.vanio.kotlin.feladat.ms.client

import hu.vanio.kotlin.feladat.ms.hu.vanio.kotlin.feladat.ms.dto.Forecast
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import kotlin.test.assertEquals

@ExtendWith(MockKExtension::class)
class WeatherClientTest{
    @InjectMockKs
    lateinit var weatherClient: WeatherClient

    @MockK
    lateinit var forecastRequest: WebClient.RequestHeadersSpec<*>

    @MockK
    lateinit var responseSpec: WebClient.ResponseSpec

    @MockK
    lateinit var forecastMono: Mono<Forecast>

    @MockK
    lateinit var forecast: Forecast

    @Test
    fun getForecast() {
        every { forecastRequest.retrieve() } returns responseSpec
        every { responseSpec.bodyToMono(Forecast::class.java) } returns forecastMono
        every { forecastMono.block() } returns forecast

        assertEquals(forecast, weatherClient.getForecast())
    }
}
