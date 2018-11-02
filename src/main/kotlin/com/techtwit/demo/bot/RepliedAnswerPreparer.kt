package com.techtwit.demo.bot

import com.techtwit.demo.service.TechTwitService
import org.springframework.stereotype.Service
import org.telegram.telegrambots.api.objects.Message

@Service
class RepliedAnswerPreparer(private val techTwitService: TechTwitService)
    : AnswerPreparer(techTwitService) {

    companion object {
        const val READ_COMMAND = "/read"
        private val READ_EMOJI = String(Character.toChars(Integer.parseInt("1F44D", 16))) // thumbs up
    }

    override fun getAnswer(message: Message): String {
        if (message.text.startsWith(READ_COMMAND)) {
            techTwitService.seenBy(message.from, message.replyToMessage.text) // TODO: Event?
            return READ_EMOJI
        }

        return DEFAULT_ANSWER
    }
}