package com.techtwit.demo

import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.dao.DuplicateKeyException
import org.springframework.util.ResourceUtils
import java.util.*


@SpringBootApplication
class TechTwitApplication {

    companion object {
        val logger = LoggerFactory.getLogger(TechTwitApplication::class.java.name)
    }

    @ConditionalOnProperty(name = ["techtwits.import.data"])
    @Bean
    fun importData(repository: TechTwitRepository) = CommandLineRunner {
        logger.info("Techtwits data importing is enabled...")

        readTechTwitsFromFile().forEach {
            logger.info("Saving into database: $it")

            try {
                repository.save(it)
            } catch (e: DuplicateKeyException) {
                logger.info("Ignoring the unique key exception...")
            }
        }
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
