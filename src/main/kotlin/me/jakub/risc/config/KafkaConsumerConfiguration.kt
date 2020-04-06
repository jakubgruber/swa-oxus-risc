package me.jakub.risc.config

import me.jakub.risc.model.request.ScoringRequest
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.support.serializer.JsonDeserializer

@Configuration
class KafkaConsumerConfiguration {

    @Value("\${spring.kafka.bootstrap-servers}")
    lateinit var bootstrapServers: String

    @Value("\${risc.kafka.topic.scoringrequest}")
    lateinit var scoringRequestTopic: String

    @Bean
    fun consumerConfigs(): Map<String, Any> {
        val props: MutableMap<String, Any> = HashMap()
        props[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapServers
        props[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        props[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = JsonDeserializer::class.java
        props[ConsumerConfig.GROUP_ID_CONFIG] = scoringRequestTopic
        return props
    }

    @Bean
    fun consumerFactory(): ConsumerFactory<String, ScoringRequest> {
        return DefaultKafkaConsumerFactory<String, ScoringRequest>(
                consumerConfigs(),
                StringDeserializer(),
                JsonDeserializer(ScoringRequest::class.java, false))
    }

    @Bean
    fun kafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, ScoringRequest>? {
        val factory: ConcurrentKafkaListenerContainerFactory<String, ScoringRequest> = ConcurrentKafkaListenerContainerFactory()
        factory.consumerFactory = consumerFactory()
        return factory
    }

}