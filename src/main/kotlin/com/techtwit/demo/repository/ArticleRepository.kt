package com.techtwit.demo.repository

import com.techtwit.demo.model.Article
import org.springframework.data.mongodb.core.FluentMongoOperations
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.aggregation.Aggregation
import org.springframework.data.mongodb.core.query.Criteria.where
import org.springframework.data.mongodb.core.query.Query.query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Repository

@Repository
class ArticleRepository(private val mongoOps: MongoOperations, private val fluentMongoOps: FluentMongoOperations) {

    companion object {
        private const val TECH_TWIT_COLLECTION = "techTwit"
        private val entityClass = Article::class.java
    }

    fun save(article: Article) = mongoOps.save(article)

    fun getRandomArticle(): Article { // TODO: do not return anything that seen by the subscriber previously.
        val matchStage = Aggregation.sample(1)
        val aggregation = Aggregation.newAggregation(matchStage)
        val output = mongoOps.aggregate(aggregation, TECH_TWIT_COLLECTION, entityClass)

        return output.uniqueMappedResult!!
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