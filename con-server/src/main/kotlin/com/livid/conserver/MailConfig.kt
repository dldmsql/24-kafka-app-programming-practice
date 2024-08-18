package com.livid.conserver

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl
import java.util.*

@Configuration
class MailConfig(
    @Value("\${spring.mail.username}") private val username: String,
    @Value("\${spring.mail.password}") private val password: String,
    @Value("\${spring.mail.host}") private val host: String,
    @Value("\${spring.mail.port}") private val port: Int

) {

    @Bean
    fun javaMailSender(): JavaMailSender {
        val javaMailSender = JavaMailSenderImpl()
        javaMailSender.host = host
        javaMailSender.username = username
        javaMailSender.password = password
        javaMailSender.port = port
        javaMailSender.javaMailProperties = getMailProperties()

        return javaMailSender
    }

    private fun getMailProperties(): Properties {
        val properties = Properties()
        properties.setProperty("mail.transport.protocol", "smtp")
        properties.setProperty("mail.smtp.auth", "true")
        properties.setProperty("mail.smtp.starttls.enable", "true")
        properties.setProperty("mail.debug", "true")
        return properties
    }
}