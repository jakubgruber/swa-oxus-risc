package me.jakub.risc.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate


@Configuration
class ServiceConfiguration {

    @Bean
    fun restTemplate(): RestTemplate = RestTemplate()

}