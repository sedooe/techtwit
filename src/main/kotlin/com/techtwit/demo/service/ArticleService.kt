package com.techtwit.demo.service

import com.techtwit.demo.model.Article
import com.techtwit.demo.repository.ArticleRepository
import org.springframework.stereotype.Service
import org.telegram.telegrambots.api.objects.User

@Service
class ArticleService(private val articleRepository: ArticleRepository) {

    fun save(article: Article) = articleRepository.save(article)

    fun getRandomArticle() = articleRepository.getRandomArticle()

    fun seenBy(user: User, source: String) {
        val articleId = articleRepository.getArticleIdBySource(source)
        articleRepository.readBySubscriber(user.id, articleId)
    }
}