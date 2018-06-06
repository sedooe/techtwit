package com.techtwit.demo.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed

data class TechTwit(@Id val id: String, @Indexed(unique = true) val source: String)