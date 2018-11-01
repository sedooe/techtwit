package com.techtwit.demo

import com.techtwit.demo.bot.TechTwitBot
import com.techtwit.demo.model.TechTwit
import com.techtwit.demo.service.TechTwitService
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.dao.DuplicateKeyException
import org.springframework.util.ResourceUtils
import org.telegram.telegrambots.ApiContextInitializer
import org.telegram.telegrambots.TelegramBotsApi
import java.util.*

@SpringBootApplication
class TechTwitApplication {

    init {
        ApiContextInitializer.init()
    }

    companion object {
        val logger = LoggerFactory.getLogger(TechTwitApplication::class.java.name)!!
    }

    @ConditionalOnProperty(name = ["techtwits.import.data"])
    @Bean
    fun importData(techTwitService: TechTwitService) = CommandLineRunner {
        logger.info("Techtwits data importing is enabled...")

        readTechTwitsFromFile().forEach {
            logger.info("Saving into database: $it")

            try {
                techTwitService.save(it)
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

    private fun readTechTwitsFromFile(): List<TechTwit> {
        return ResourceUtils.getFile("classpath:tweets.txt")
                .useLines { it.toList() }
                .map { TechTwit(UUID.randomUUID().toString(), it.substringAfter("> ")) }
    }
}

fun main(args: Array<String>) {
    runApplication<TechTwitApplication>(*args)
}
