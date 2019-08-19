package io.github.oxnz.Ingrid;

import io.github.oxnz.Ingrid.article.Article;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootApplication
public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        log.info("starting ...");
        SpringApplication.run(Application.class, args);
        log.debug("--Application Started--");
    }

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(new RedisStandaloneConfiguration("localhost", 6379));
    }

    @Bean
    StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        return new StringRedisTemplate(redisConnectionFactory);
    }

    @Bean
    RedisTemplate<Long, Article> articleRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Long, Article> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }

}
