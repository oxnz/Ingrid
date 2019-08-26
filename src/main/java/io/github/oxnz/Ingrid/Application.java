package io.github.oxnz.Ingrid;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.fasterxml.jackson.module.scala.DefaultScalaModule;
import io.github.oxnz.Ingrid.article.Article;
import io.github.oxnz.Ingrid.dts.mq.RedisMessageConsumer;
import io.github.oxnz.Ingrid.dts.mq.RedisMessageProducer;
import io.github.oxnz.Ingrid.dts.mq.TxEvent;
import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.config.NamingConvention;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@SpringBootApplication
public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        log.info("starting ...");
        SpringApplication.run(Application.class, args);
        log.debug("--Application Started--");
    }

    @Bean
    MeterRegistryCustomizer<MeterRegistry> metricsCommonTags() {
        return registry -> registry.config().commonTags("app", "ingrid");
    }

    @Bean
    MeterRegistryCustomizer<SimpleMeterRegistry> metricsNamingConvention() {
        return registry -> registry.config().namingConvention(new NamingConvention() {
            @Override
            public String name(String name, Meter.Type type, String baseUnit) {
                String suffix;
                switch (type) {
                    case TIMER:
                        suffix = "timer";
                        break;
                    case COUNTER:
                        suffix = "counter";
                        break;
                    default:
                        suffix = type.name();
                }
                return String.format("%s.%s.%s", name, suffix, baseUnit);
            }
        });
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper()
                .registerModule(new DefaultScalaModule())
                .registerModule(new ParameterNamesModule())
                .registerModule(new Jdk8Module())
                .registerModule(new JavaTimeModule()); // new module, NOT JSR310Module
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

    @Bean
    RedisTemplate<Long, TxEvent> txEventRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Long, TxEvent> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }

    /**
     * redis MQ
     */

    @Bean
    ChannelTopic channelTopic() {
        return new ChannelTopic("dts");
    }

    @Bean
    public MessageListenerAdapter messageListenerAdapter(RedisMessageConsumer redisMessageConsumer) {
        return new MessageListenerAdapter(redisMessageConsumer);
    }

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory redisConnectionFactory,
                                                                       MessageListenerAdapter messageListenerAdapter,
                                                                       ChannelTopic channelTopic) {
        RedisMessageListenerContainer container
                = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);
        container.addMessageListener(messageListenerAdapter, channelTopic);
        return container;
    }


}
