package me.jakub.risc.service

import com.fasterxml.jackson.databind.ObjectMapper
import me.jakub.risc.model.request.ScoringRequest
import me.jakub.risc.model.result.ScoringResult
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class KafkaScoringClient(
        private val scoringService: CustomerScoringService,
        private val objectMapper: ObjectMapper,
        private val kafkaTemplate: KafkaTemplate<String, Any>) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @Value("\${risc.kafka.topic.scoringresult}")
    private lateinit var resultTopic: String

    @KafkaListener(topics = ["\${risc.kafka.topic.scoringrequest}"], containerFactory = "kafkaListenerContainerFactory")
    fun processMessage(scoringRequest: ScoringRequest) {
        logger.info("> processMessage - $scoringRequest")

        val customerResult = scoringService.scoreCustomer(scoringRequest)

        val scoringResult = objectMapper.convertValue(scoringRequest, ScoringResult::class.java)
        scoringResult.result = customerResult

        publishResult(scoringResult)
    }

    private fun publishResult(scoringResult: ScoringResult) {
        logger.info("> publishResponse - {}", scoringResult)

        kafkaTemplate.send(resultTopic, scoringResult)
    }

}