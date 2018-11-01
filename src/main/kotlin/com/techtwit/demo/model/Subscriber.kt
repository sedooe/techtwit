package com.techtwit.demo.model

import org.springframework.data.annotation.Id

data class Subscriber(@Id val id: Int, val lastArticleSent: String = "")