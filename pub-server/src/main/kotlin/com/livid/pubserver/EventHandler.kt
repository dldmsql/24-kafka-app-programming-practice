package com.livid.pubserver

import com.livid.adapterkafka.KafkaUtil
import com.livid.adapterkafka.MailDto
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono


@Component
class EventHandler(
    private val kafkaUtil: KafkaUtil
) {
    fun createEvent(request: ServerRequest): Mono<ServerResponse> {
        val req: Mono<MailDto> = request.bodyToMono(
            MailDto::class.java
        )

        val saved = req
            .flatMap {
                kafkaUtil.send(
                    this.kafkaUtil.getTopicList()[KafkaUtil.Companion.TopicMapKey.EMAIL_EVENT]!!,
                    MailDto::class.java.simpleName,
                    it
                )
            }

        return ServerResponse.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body<Boolean, Mono<Boolean>>(
                saved,
                Boolean::class.java
            )
    }


}