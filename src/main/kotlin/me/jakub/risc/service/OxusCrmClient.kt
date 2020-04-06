package me.jakub.risc.service

import me.jakub.risc.model.client.OxusClientData
import me.jakub.risc.model.client.OxusCrmResponse
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class OxusCrmClient(private val restTemplate: RestTemplate) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @Value("\${oxus.crm.url}")
    private lateinit var baseUrl: String

    fun getCustomerInfo(id: Int): OxusClientData? {
        logger.info("> getCustomerInfo - {}", id)

        val oxusResponse = restTemplate.getForEntity("$baseUrl/api/v1/client/$id", OxusCrmResponse::class.java)

        logger.info("< getCustomerInfo - {}", oxusResponse)
        return oxusResponse.body?.clientData
    }

}