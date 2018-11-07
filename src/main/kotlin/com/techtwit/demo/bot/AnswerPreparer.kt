package com.techtwit.demo.bot

import com.techtwit.demo.service.ArticleService
import org.telegram.telegrambots.api.objects.Message

abstract class AnswerPreparer(private val articleService: ArticleService) {

    companion object {
        private val defaultAnswerEmoji = String(Character.toChars(Integer.parseInt("1F914", 16))) // thinking face

        @JvmStatic
        protected val defaultAnswer =  "Didn't get it $defaultAnswerEmoji"
    }

    abstract fun getAnswer(message: Message): String
}