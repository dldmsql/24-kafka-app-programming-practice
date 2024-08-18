package com.livid.adapterkafka

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import reactor.core.scheduler.Schedulers
import reactor.kafka.receiver.KafkaReceiver
import reactor.kafka.receiver.ReceiverOptions
import reactor.kafka.sender.KafkaSender
import reactor.kafka.sender.SenderOptions
import java.time.Duration

@Configuration
class KafkaConfig(
    @Value("\${kafka.host}")
    private val host: String,
    @Value("\${kafka.topic}")
    private val topic: String,
    @Value("\${kafka.group-con}")
    private val groupCon: String
) {
    @Bean("kafkaSender")
    fun kafkaSender(): KafkaSender<String, Any> {
        val senderOptions = SenderOptions.create<String, Any>(getProducerProps())
        senderOptions.scheduler(Schedulers.parallel())
        senderOptions.closeTimeout(Duration.ofSeconds(3))
        return KafkaSender.create(senderOptions)
    }

    private fun getProducerProps(): Map<String, Any> {
        return mapOf(
            ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to host,
            ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
            ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
            ProducerConfig.ACKS_CONFIG to "0", // acks를 0으로 설정
            ProducerConfig.RETRIES_CONFIG to "0", // retries를 0으로 설정
            ProducerConfig.MAX_BLOCK_MS_CONFIG to 30000,
            ProducerConfig.MAX_REQUEST_SIZE_CONFIG to 4375412
        )
    }

    @Bean
    fun kafkaReceiver(kafkaReceiverOptionsForNotice: ReceiverOptions<String, String>): KafkaReceiver<String, String> {
        val topics = listOf(topic)
        return KafkaReceiver.create(kafkaReceiverOptionsForNotice.subscription(topics))
    }

    @Bean("kafkaReceiverOptions")
    fun kafkaReceiverOptions(): ReceiverOptions<String, String> {
        val props = mapOf(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to host,
            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
            ConsumerConfig.GROUP_ID_CONFIG to groupCon,
            ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG to 4375412,
            ConsumerConfig.MAX_POLL_RECORDS_CONFIG to 1
        )
        return ReceiverOptions.create<String, String>(props)
    }
}