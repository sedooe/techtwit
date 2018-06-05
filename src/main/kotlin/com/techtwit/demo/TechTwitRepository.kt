package com.techtwit.demo

import org.springframework.data.mongodb.repository.MongoRepository

interface TechTwitRepository : MongoRepository<TechTwit, Long>