package com.techtwit.demo.bot

import com.techtwit.demo.service.SubscriberService
import com.techtwit.demo.service.ArticleService
import org.springframework.stereotype.Service
import org.telegram.telegrambots.api.objects.Message

@Service
class NormalAnswerPreparer(val articleService: ArticleService, val subscriberService: SubscriberService)
    : AnswerPreparer(articleService) {

    companion object {
        private const val START_COMMAND = "/start"
        private const val NEW_SOURCE_COMMAND = "/new"

        private val startEmoji = String(Character.toChars(Integer.parseInt("1F60A", 16))) // smiling face
    }

    override fun getAnswer(message: Message): String {
        val messageStartsWith: (String) -> Boolean = { message.text.startsWith(it) }

        return when {
            messageStartsWith(START_COMMAND) -> {
                subscriberService.save(message.from) // TODO: Event?
                return startEmoji
            }

            messageStartsWith(NEW_SOURCE_COMMAND) -> articleService.getRandomArticle().source

            messageStartsWith(RepliedAnswerPreparer.READ_COMMAND) -> "You need to reply one of my messages to use this command."

            else -> defaultAnswer
        }
    }
}