package com.techtwit.demo.service

import com.techtwit.demo.model.TechTwit
import com.techtwit.demo.repository.TechTwitRepository
import org.springframework.stereotype.Service
import org.telegram.telegrambots.api.objects.User

@Service
class TechTwitService(private val techTwitRepository: TechTwitRepository) {

    fun save(techTwit: TechTwit) = techTwitRepository.save(techTwit)

    fun getRandomTechTwit() = techTwitRepository.getRandomTechTwit()

    fun seenBy(user: User, source: String) {
        val articleId = techTwitRepository.getArticleIdBySource(source)
        techTwitRepository.readBySubscriber(user.id, articleId)
    }
}