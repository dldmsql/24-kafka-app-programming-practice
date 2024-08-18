package com.livid.pubserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["com.livid"])
class PubServerApplication

fun main(args: Array<String>) {
    runApplication<PubServerApplication>(*args)
}
