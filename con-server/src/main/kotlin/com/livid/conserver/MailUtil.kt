package com.livid.conserver

import com.livid.adapterkafka.KafkaUtil
import com.livid.adapterkafka.MailDto
import jakarta.mail.Message
import jakarta.mail.MessagingException
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Component

@Component
class MailUtil(
    private val javaMailSender: JavaMailSender
) {
    fun sendMail(mailDto: MailDto) {
        // send mail
        try {
            val message = javaMailSender.createMimeMessage()
            message.subject = mailDto.subject
            message.setText(mailDto.text)
            message.setRecipients(Message.RecipientType.TO, mailDto.to)
            message.setContent(mailDto.text, "text/html; charset=utf-8")
            javaMailSender.send(message)

        } catch (e: MessagingException) {
            e.printStackTrace()
        }
    }

}