package com.techtwit.demo.repository

import com.techtwit.demo.model.TechTwit
import org.springframework.data.mongodb.core.FluentMongoOperations
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.aggregation.Aggregation
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Criteria.where
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Query.query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Repository

@Repository
class TechTwitRepository(private val mongoOps: MongoOperations, private val fluentMongoOps: FluentMongoOperations) {

    companion object {
        private const val TECH_TWIT_COLLECTION = "techTwit"
    }

    fun save(techTwit: TechTwit) = mongoOps.save(techTwit)

    fun getRandomTechTwit(): TechTwit {
        val matchStage = Aggregation.sample(1)
        val aggregation = Aggregation.newAggregation(matchStage)
        val output = mongoOps.aggregate(aggregation, TECH_TWIT_COLLECTION, TechTwit::class.java)

        return output.uniqueMappedResult!!
    }

    fun getArticleIdBySource(source: String): String {
        val query = query(where("source").`is`(source))
        query.fields().include("id") // not really fluentMongoOps, huh?

        return fluentMongoOps.query(TechTwit::class.java).`as`(ArticleIdOnly::class.java)
                .matching(query).firstValue()!!.id
    }

    fun readBySubscriber(subscriberId: Int, articleId: String) {
        mongoOps.updateFirst(
                Query.query(Criteria.where("id").`is`(articleId)),
                Update().push("seenBySubscribers", subscriberId),
                TechTwit::class.java
        )
    }
}

private class ArticleIdOnly(val id: String)