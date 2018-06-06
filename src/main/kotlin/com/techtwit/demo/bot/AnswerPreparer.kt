package com.techtwit.demo.bot

import com.techtwit.demo.service.TechTwitService
import org.telegram.telegrambots.api.objects.Message

abstract class AnswerPreparer(private val techTwitService: TechTwitService) {

    companion object {
        private val DEFAULT_ANSWER_EMOJI = String(Character.toChars(Integer.parseInt("1F914", 16))) // thinking face

        @JvmStatic
        protected val DEFAULT_ANSWER =  "Didn't get it $DEFAULT_ANSWER_EMOJI"
    }

    abstract fun getAnswer(message: Message): String
}