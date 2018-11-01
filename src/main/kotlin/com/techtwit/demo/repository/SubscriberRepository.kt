package com.techtwit.demo.repository

import com.techtwit.demo.model.Subscriber
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.query.Criteria.where
import org.springframework.data.mongodb.core.query.Query.query
import org.springframework.data.mongodb.core.query.Update.update
import org.springframework.stereotype.Repository
import org.telegram.telegrambots.api.objects.User

@Repository
class SubscriberRepository(private val mongoOps: MongoOperations) {

    fun save(user: User) = mongoOps.save(Subscriber(user.id))

    fun getLastSentArticle(user: User): String {
        val query = query(where("id").`is`(user.id))
        query.fields().include("lastArticleSent") // not fluent API

        return mongoOps.findOne(query, String::class.java, "subscriber")!!
    }

    fun updateLastSentArticle(user: User, articleId: String) {
        mongoOps.updateFirst(
                query(where("id").`is`(user.id)),
                update("lastArticleSent", articleId),
                Subscriber::class.java
        )
    }
}