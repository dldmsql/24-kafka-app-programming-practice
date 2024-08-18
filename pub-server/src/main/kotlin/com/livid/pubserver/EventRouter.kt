package com.livid.pubserver

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.RequestPredicates.POST
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions
import org.springframework.web.reactive.function.server.ServerResponse


@Configuration
class EventRouter {

    @Bean
    fun createEvent(handler: EventHandler): RouterFunction<ServerResponse> {
        return RouterFunctions
            .route(
                POST("/api/email"),
                handler::createEvent
            )
    }
}