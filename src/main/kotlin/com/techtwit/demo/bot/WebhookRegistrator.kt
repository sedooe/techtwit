package com.techtwit.demo.bot

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.client.RestTemplate

@Component
class WebhookRegistrator(
        @Value("\${telegram-api.set-webhook-url}") private val setWebhookUrl: String,
        @Value("\${techtwits.bot.webhook-callback-url}") private val callbackUrl: String) {

    init {
        val requestBody = LinkedMultiValueMap<String, String>().apply { add("url", callbackUrl) }
        val requestHeaders = HttpHeaders().apply { contentType = MediaType.APPLICATION_FORM_URLENCODED }
        val request = HttpEntity(requestBody, requestHeaders)

        val restTemplate = RestTemplate()
        val response = restTemplate.postForEntity(setWebhookUrl, request, String::class.java)

        if (!response.statusCode.is2xxSuccessful) {
            throw IllegalStateException("Cannot set webhook")
        }
    }
}
