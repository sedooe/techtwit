package com.techtwit.demo.bot

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.telegram.telegrambots.api.methods.send.SendMessage
import org.telegram.telegrambots.api.objects.Update

@RestController
@RequestMapping("/webhook")
class WebhookController {

    @PostMapping("/{token}")
    fun webhook(@PathVariable token: String, @RequestBody update: Update): SendMessage {
        return SendMessage(update.message.chatId, update.message.text)
    }
}