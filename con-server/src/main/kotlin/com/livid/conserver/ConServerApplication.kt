package com.livid.conserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["com.livid"])
class ConServerApplication

fun main(args: Array<String>) {
    runApplication<ConServerApplication>(*args)
}
