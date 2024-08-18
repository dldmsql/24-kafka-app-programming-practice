package com.livid.adapterkafka

data class MailDto(
    val to: String,
    val subject: String,
    val text: String
)
