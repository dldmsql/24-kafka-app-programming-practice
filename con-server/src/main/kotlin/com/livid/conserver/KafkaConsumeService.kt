package com.livid.conserver

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.livid.adapterkafka.KafkaUtil
import com.livid.adapterkafka.MailDto
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.kafka.receiver.KafkaReceiver

@Service
class KafkaConsumeService (
private val kafkaReceiver: KafkaReceiver<String, String>,
    private val kafkaUtil: KafkaUtil,
    private val mailUtil: MailUtil
) : ApplicationRunner {

    override fun run(args: ApplicationArguments?) {

        val topic =
            this.kafkaUtil.getTopicList()[KafkaUtil.Companion.TopicMapKey.EMAIL_EVENT]!!

        this.kafkaReceiver.receive()
            .flatMap {
                val kafkaReceiverDto = KafkaUtil.Companion.KafkaReceiverDto(
                    it.topic(),
                    it.key(),
                    it.value()
                )
                when (it.topic()) {
                    topic -> {
                        sendEmail(kafkaReceiverDto)
                    }
                    else -> {
                    }
                }
                Flux.just(it)
            }
            .doOnNext {
                it.receiverOffset().acknowledge()
            }
            .doOnError { e: Any? ->
                e is Throwable
            }
            .subscribe()
    }

    private fun sendEmail(kafkaReceiverDto: KafkaUtil.Companion.KafkaReceiverDto) {
        val emailEvent = jacksonObjectMapper().readValue(kafkaReceiverDto.value, MailDto::class.java)
        mailUtil.sendMail(emailEvent)
    }
}