package hu.vanio.kotlin.feladat.ms.controller

import com.ninjasquad.springmockk.MockkBean
import hu.vanio.kotlin.feladat.ms.hu.vanio.kotlin.feladat.ms.controller.IndexController
import hu.vanio.kotlin.feladat.ms.hu.vanio.kotlin.feladat.ms.service.WeatherService
import io.mockk.every
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.model
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.time.LocalDate

@SpringBootTest
class IndexControllerTest {

    @MockkBean
    lateinit var weatherService: WeatherService

    lateinit var mockMvc: MockMvc

    @BeforeEach fun init() {
        mockMvc = MockMvcBuilders.standaloneSetup(IndexController(weatherService)).build()
    }

    @Test
    fun `when call index page`() {
        every { weatherService.getDailyAverages() } returns dailyAverages

        mockMvc
            .perform(get("/"))
            .andExpect(status().isOk)
            .andExpect(model().attribute("dailyAverages", dailyAverages))
    }

    @Test
    fun `when call index page if service unavailable`() {
        every { weatherService.getDailyAverages() } throws RuntimeException()

        mockMvc
            .perform(get("/"))
            .andExpect(status().isOk)
            .andExpect(model().attribute("dailyAverages", emptyMap<Any, Any>()))
            .andExpect(model().attribute("error", "Service unavailable"))
    }

}

val DATE: LocalDate = LocalDate.of(2024, 2, 21)
const val VALUE = 9.87
val dailyAverages = mapOf(DATE to VALUE)
