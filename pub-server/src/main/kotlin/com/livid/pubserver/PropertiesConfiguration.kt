package com.livid.pubserver

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource


@Configuration
@PropertySource(
    ignoreResourceNotFound = true,
    value = [
        "classpath:application-kafka.yml",
        "classpath:application.yml"
    ],
    factory = YamlPropertySourceFactory::class
)
class PropertiesConfiguration {
}