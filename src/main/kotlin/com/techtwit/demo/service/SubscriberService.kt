package com.techtwit.demo.service

import com.techtwit.demo.repository.SubscriberRepository
import org.springframework.stereotype.Service
import org.telegram.telegrambots.api.objects.User

@Service
class SubscriberService(private val subscriberRepository: SubscriberRepository) {

    fun save(user: User) = subscriberRepository.save(user)

    fun getLastArticleSentTo(user: User) = subscriberRepository.getLastSentArticle(user)

    fun updateLastArticleSent(user: User, articleId: String) = subscriberRepository.updateLastSentArticle(user, articleId)
}