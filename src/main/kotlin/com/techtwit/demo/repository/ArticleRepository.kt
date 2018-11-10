package com.techtwit.demo.repository

import com.techtwit.demo.model.Article
import org.springframework.data.mongodb.core.FluentMongoOperations
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.aggregation.Aggregation.*
import org.springframework.data.mongodb.core.query.Criteria.where
import org.springframework.data.mongodb.core.query.Query.query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Repository
import org.telegram.telegrambots.api.objects.User

@Repository
class ArticleRepository(private val mongoOps: MongoOperations, private val fluentMongoOps: FluentMongoOperations) {

    companion object {
        private const val ARTICLE_COLLECTION = "article"
        private val entityClass = Article::class.java
    }

    fun save(article: Article) = mongoOps.save(article)

    fun getRandomArticleFor(user: User): Article? {
        val matchOperation = match(where("seenBySubscribers").not().all(user.id))
        val aggregation = newAggregation(matchOperation, sample(1))

        val result = mongoOps.aggregate(aggregation, ARTICLE_COLLECTION, entityClass)

        return result.uniqueMappedResult
    }

    fun getArticleIdBySource(source: String): String {
        val query = query(where("source").`is`(source))
        query.fields().include("id") // not really fluentMongoOps, huh?

        return fluentMongoOps.query(entityClass).`as`(ArticleIdOnly::class.java)
                .matching(query).firstValue()!!.id
    }

    fun readBySubscriber(subscriberId: Int, articleId: String) {
        mongoOps.updateFirst(
                query(where("id").`is`(articleId)),
                Update().push("seenBySubscribers", subscriberId),
                entityClass
        )
    }

    private class ArticleIdOnly(val id: String)
}