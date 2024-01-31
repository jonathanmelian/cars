package com.example.demo.config

import java.time.Duration
import org.springframework.cache.annotation.CachingConfigurerSupport
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.RedisSerializer


@Configuration
@EnableCaching
class CachingConfig : CachingConfigurerSupport() {


    @Bean
    fun redisConnectionFactory(): LettuceConnectionFactory {
        return LettuceConnectionFactory(RedisStandaloneConfiguration("localhost", 6379))
    }

    @Bean
    fun redisTemplate(): RedisTemplate<Any, Any> {
        val redisTemplate = RedisTemplate<Any, Any>()
        redisTemplate.connectionFactory = redisConnectionFactory()
        return redisTemplate
    }

    @Bean
    @Primary
    fun redisCacheManager(lettuceConnectionFactory: LettuceConnectionFactory?): RedisCacheManager {
        val redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig().disableCachingNullValues()
            .entryTtl(Duration.ofMinutes(1))
            .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.json()))
        redisCacheConfiguration.usePrefix()
        return RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(lettuceConnectionFactory!!)
            .cacheDefaults(redisCacheConfiguration).build()
    }

}