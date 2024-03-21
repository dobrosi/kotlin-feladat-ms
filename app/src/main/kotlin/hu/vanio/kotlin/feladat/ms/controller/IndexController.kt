package hu.vanio.kotlin.feladat.ms.hu.vanio.kotlin.feladat.ms.controller

import hu.vanio.kotlin.feladat.ms.hu.vanio.kotlin.feladat.ms.service.WeatherService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.servlet.ModelAndView

@Controller
class IndexController(@Autowired val weatherService: WeatherService) {
    @GetMapping("/")
    fun dailyAverages(): ModelAndView = modelAndView()
        .addObject("dailyAverages", weatherService.getDailyAverages())

    @ExceptionHandler(RuntimeException::class)
    fun error(): ModelAndView = modelAndView()
        .addObject("dailyAverages", emptyMap<Any, Any>())
        .addObject("error", SERVICE_UNAVAILABLE)

    private fun modelAndView() = ModelAndView("dailyAverages")
}

const val SERVICE_UNAVAILABLE = "Service unavailable"
