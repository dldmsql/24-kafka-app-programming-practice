package com.livid.adapterkafka

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AdapterKafkaApplication

fun main(args: Array<String>) {
    runApplication<AdapterKafkaApplication>(*args)
}
