package hu.vanio.kotlin.feladat.ms.hu.vanio.kotlin.feladat.ms.configuration

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class WeatherAppConfiguration {
    @Bean
    fun webClient(): WebClient = WebClient.builder().build()

    @Bean
    fun uriSpec(@Autowired webClient: WebClient): WebClient.RequestHeadersUriSpec<*> = webClient.get()

    @Bean
    fun forecast(
            @Autowired uriSpec: WebClient.RequestHeadersUriSpec<*>,
            @Value("\${forecast-url}") weatherApiUrl: String
    ): WebClient.RequestHeadersSpec<*> = uriSpec.uri(weatherApiUrl)
}
