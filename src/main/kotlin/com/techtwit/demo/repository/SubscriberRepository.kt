package com.techtwit.demo.repository

import com.techtwit.demo.model.Subscriber
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.stereotype.Repository
import org.telegram.telegrambots.api.objects.User

@Repository
class SubscriberRepository(private val mongoOps: MongoOperations) {

    fun save(user: User) = mongoOps.save(Subscriber(user.id))
}