package com.techtwit.demo

import com.techtwit.demo.bot.TechTwitBot
import com.techtwit.demo.model.Article
import com.techtwit.demo.service.ArticleService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.dao.DuplicateKeyException
import org.telegram.telegrambots.ApiContextInitializer
import org.telegram.telegrambots.TelegramBotsApi
import java.io.File
import java.util.*

@SpringBootApplication
class TechTwitApplication {

    init {
        ApiContextInitializer.init()
    }

    companion object {
        val logger = LoggerFactory.getLogger(TechTwitApplication::class.java.name)!!
    }

    @ConditionalOnProperty(name = ["techtwits.data.import"])
    @Bean
    fun importData(articleService: ArticleService,
                   @Value("\${techtwits.data.resourceFilePath}") resourceFilePath: String) = CommandLineRunner {
        logger.info("Techtwits data importing is enabled...")

        readTechTwitsFrom(resourceFilePath).forEach {
            logger.info("Saving into database: $it")

            try {
                articleService.save(it)
            } catch (e: DuplicateKeyException) {
                logger.info("Ignoring the unique key exception...")
            }
        }
    }

    @Bean
    fun initBot(techTwitBot: TechTwitBot) = CommandLineRunner {
        val telegramBotsApi = TelegramBotsApi()
        telegramBotsApi.registerBot(techTwitBot)
    }

    private fun readTechTwitsFrom(resourceFilePath: String): List<Article> {
        return File(resourceFilePath)
                .useLines { it.toList() }
                .map { Article(UUID.randomUUID().toString(), it.substringAfter("> ")) }
    }
}

fun main(args: Array<String>) {
    runApplication<TechTwitApplication>(*args)
}
