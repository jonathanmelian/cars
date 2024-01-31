package com.example.demo.controller.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable
import org.springframework.data.redis.core.RedisHash

data class Car (
    val id: Int? = null,
    val brand: String? = null,
    val model: String? = null,
    val color: String? = null,
) : Serializable

