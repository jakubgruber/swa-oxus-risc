package me.jakub.risc.service

import me.jakub.risc.model.request.ScoringRequest
import me.jakub.risc.model.result.CustomerResultEnum
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class CustomerScoringService(private val oxusCrmClient: OxusCrmClient) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    fun scoreCustomer(scoringRequest: ScoringRequest): CustomerResultEnum {
        logger.info("> scoreCustomer - {}", scoringRequest)

        val clientData = oxusCrmClient.getCustomerInfo(scoringRequest.clientId)

        clientData ?: throw RuntimeException("Failed to find client by id - ${scoringRequest.clientId}")

        return if ((clientData.income - clientData.expense) * 60L.toBigDecimal() > scoringRequest.loanAmount) {
            CustomerResultEnum.OK
        } else {
            CustomerResultEnum.NOT_OK
        }
    }

}