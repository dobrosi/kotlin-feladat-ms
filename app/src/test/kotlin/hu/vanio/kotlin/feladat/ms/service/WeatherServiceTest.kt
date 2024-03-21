package hu.vanio.kotlin.feladat.ms.service

import com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut
import hu.vanio.kotlin.feladat.ms.hu.vanio.kotlin.feladat.ms.client.WeatherClient
import hu.vanio.kotlin.feladat.ms.hu.vanio.kotlin.feladat.ms.dto.Forecast
import hu.vanio.kotlin.feladat.ms.hu.vanio.kotlin.feladat.ms.dto.Hourly
import hu.vanio.kotlin.feladat.ms.hu.vanio.kotlin.feladat.ms.service.WeatherService
import io.mockk.InternalPlatformDsl.toStr
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.io.ByteArrayOutputStream
import java.time.LocalDate
import java.time.LocalDateTime

@ExtendWith(MockKExtension::class)
class WeatherServiceTest {
    @InjectMockKs
    lateinit var weatherService: WeatherService

    @MockK
    lateinit var weatherClient: WeatherClient

    @BeforeEach
    fun setUp() {
        every { weatherClient.getForecast() } returns FORECAST
    }

    @Test
    fun postConstruct() {
        assertEquals(EXPECTED_JSON, tapSystemOut { weatherService.postConstruct() })
    }

    @Test
    fun printDailyAverages() {
        val byteArrayOutputStream = ByteArrayOutputStream()

        weatherService.printDailyAverages(byteArrayOutputStream)

        assertEquals(EXPECTED_JSON, byteArrayOutputStream.toStr())
    }

    @Test
    fun getDailyAverages() {
        assertThat(weatherService.getDailyAverages()).isEqualTo(
            mapOf(
                LocalDate.of(2024, 5, 28) to 15.0,
                LocalDate.of(2024, 5, 29) to 16.0
            )
        )
    }

    @Test
    fun getHourly() {
        assertThat(weatherService.getHourly()).isEqualTo(
            listOf(
                Pair(DATE1, VALUE1),
                Pair(DATE2, VALUE2),
                Pair(DATE3, VALUE3),
                Pair(DATE4, VALUE4),
                Pair(DATE5, VALUE5),
                Pair(DATE6, VALUE6)
            )
        )
    }

    @Test
    fun getWeatherClient() {
        assertEquals(weatherClient, weatherService.weatherClient)
    }
}

val DATE1: LocalDateTime = LocalDateTime.of(2024, 5, 28, 0, 0)
const val VALUE1: Double = 12.0
val DATE2: LocalDateTime = LocalDateTime.of(2024, 5, 28, 12, 0)
const val VALUE2: Double = 18.0
val DATE3: LocalDateTime = LocalDateTime.of(2024, 5, 29, 0, 0)
const val VALUE3: Double = 13.0
val DATE4: LocalDateTime = LocalDateTime.of(2024, 5, 29, 6, 0)
const val VALUE4: Double = 15.0
val DATE5: LocalDateTime = LocalDateTime.of(2024, 5, 29, 12, 0)
const val VALUE5: Double = 20.0
val DATE6: LocalDateTime = LocalDateTime.of(2024, 5, 29, 18, 0)
const val VALUE6: Double = 16.0
val HOURLY =
    Hourly(
        listOf(
            DATE1,
            DATE2,
            DATE3,
            DATE4,
            DATE5,
            DATE6
        ),
        listOf(
            VALUE1,
            VALUE2,
            VALUE3,
            VALUE4,
            VALUE5,
            VALUE6
        )
    )
val FORECAST = Forecast(HOURLY)
const val EXPECTED_JSON = "{\"2024-05-28\":15.0,\"2024-05-29\":16.0}"
