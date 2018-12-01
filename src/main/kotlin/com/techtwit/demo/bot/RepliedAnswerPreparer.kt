package com.techtwit.demo.bot

import com.techtwit.demo.service.ArticleService
import org.springframework.stereotype.Service
import org.telegram.telegrambots.api.objects.Message

@Service
class RepliedAnswerPreparer(private val articleService: ArticleService)
    : AnswerPreparer(articleService) {

    companion object {
        const val READ_COMMAND = "/read"
        private val readEmoji = String(Character.toChars(Integer.parseInt("1F44D", 16))) // thumbs up
    }

    override fun getAnswer(message: Message): String {
        if (message.text.startsWith(READ_COMMAND)) {
            articleService.seenBy(message.from, message.replyToMessage.text)
            return readEmoji
        }

        return defaultAnswer
    }
}