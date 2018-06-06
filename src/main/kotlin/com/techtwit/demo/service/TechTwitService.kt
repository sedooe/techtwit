package com.techtwit.demo.service

import com.techtwit.demo.model.TechTwit
import com.techtwit.demo.repository.TechTwitRepository
import org.springframework.stereotype.Service
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.aggregation.Aggregation

@Service
class TechTwitService(private val techTwitRepository: TechTwitRepository, private val mongoTemplate: MongoTemplate) {

    companion object {
        private const val TECH_TWIT_COLLECTION = "techTwit"
    }

    fun getRandomTechTwit(): TechTwit {
        val matchStage = Aggregation.sample(1)
        val aggregation = Aggregation.newAggregation(matchStage)
        val output = mongoTemplate.aggregate(aggregation, TECH_TWIT_COLLECTION, TechTwit::class.java)

        return output.uniqueMappedResult!!
    }
}