package com.livid.adapterkafka

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.annotation.PreDestroy
import org.apache.kafka.clients.producer.ProducerRecord
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.kafka.sender.KafkaSender

@Component
class KafkaUtil(
    private val kafkaSender: KafkaSender<String, Any>,
    val objectMapper: ObjectMapper,
    @Value("\${kafka.topic}")
    private val topic: String,
) {
    fun send(topic: String, key: String, value: Any): Mono<Boolean> {
        return this.kafkaSender.createOutbound()
            .send(
                Mono.just(
                    ProducerRecord(
                        topic,
                        key,
                        this.objectMapper.writeValueAsString(value)
                    )
                )
            )
            .then()
            .then(Mono.fromCallable { true })
            .onErrorResume {
                Mono.just(false)
            }
    }

    companion object {
        enum class TopicMapKey {
            EMAIL_EVENT,
        }

        data class KafkaReceiverDto(
            val topic: String,
            val key: String?,
            val value: String
        )

    }

    fun getTopicList(): Map<TopicMapKey, String> {
        return mapOf(
            TopicMapKey.EMAIL_EVENT to topic,
        )
    }

    @PreDestroy
    fun destroy() {
        this.kafkaSender.close()
    }
}