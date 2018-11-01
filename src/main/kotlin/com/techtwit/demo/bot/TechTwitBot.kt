package com.techtwit.demo.bot

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.telegram.telegrambots.api.methods.send.SendMessage
import org.telegram.telegrambots.api.objects.Update
import org.telegram.telegrambots.bots.TelegramLongPollingBot

@Component
class TechTwitBot(
        @Value("\${techtwits.bot.token}") private val token: String,
        @Value("\${techtwits.bot.username}") private val botUsername: String,
        private val normalAnswerPreparer: NormalAnswerPreparer,
        private val repliedAnswerPreparer: RepliedAnswerPreparer): TelegramLongPollingBot() {

    override fun getBotToken() = token

    override fun getBotUsername() = botUsername

    override fun onUpdateReceived(update: Update) {

        if (!update.hasMessage() || !update.message.hasText()) {
            return
        }

        val message = update.message

        if (message.isReply) {
            execute(SendMessage(message.chatId, repliedAnswerPreparer.getAnswer(message)))
            return
        }

        execute(SendMessage(message.chatId, normalAnswerPreparer.getAnswer(message)))
    }
}