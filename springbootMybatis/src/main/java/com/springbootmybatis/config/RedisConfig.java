package com.springbootmybatis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;


@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String,Object>redisTemplate(RedisConnectionFactory redisConnectionFactory){

        RedisTemplate<String,Object>redisTemplate=new RedisTemplate<>();
        //key的序列化方法
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //value设置序列化的方式
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());

        //设置hash类型的序列化
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        //设置连接工厂
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }


}
