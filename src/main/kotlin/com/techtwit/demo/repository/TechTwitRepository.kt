package com.techtwit.demo.repository

import com.techtwit.demo.model.TechTwit
import org.springframework.data.mongodb.repository.MongoRepository

interface TechTwitRepository : MongoRepository<TechTwit, Long>