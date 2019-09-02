package io.github.oxnz.Ingrid

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import io.github.oxnz.Ingrid.article.Article
import io.github.oxnz.Ingrid.cx.{CheckinDataCompExecutor, CxExecutor, CxService}
import io.github.oxnz.Ingrid.tx._
import io.micrometer.core.instrument.config.NamingConvention
import io.micrometer.core.instrument.simple.SimpleMeterRegistry
import io.micrometer.core.instrument.{Meter, MeterRegistry}
import org.slf4j.LoggerFactory
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.connection.{RedisConnectionFactory, RedisStandaloneConfiguration}
import org.springframework.data.redis.core.{RedisTemplate, StringRedisTemplate}
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter
import org.springframework.data.redis.listener.{ChannelTopic, RedisMessageListenerContainer}

import scala.jdk.CollectionConverters._


object Application {
  private val log = LoggerFactory.getLogger(classOf[Application])

  def main(args: Array[String]): Unit = {
    log.info("starting ...")
    SpringApplication.run(classOf[Application], args: _*)
    log.debug("--Application Started--")
  }
}

@SpringBootApplication class Application {
  @Bean private[Ingrid] def metricsCommonTags = (registry: MeterRegistry) => registry.config.commonTags("app", "ingrid")

  @Bean private[Ingrid] def metricsNamingConvention = (registry: SimpleMeterRegistry) => registry.config.namingConvention(new NamingConvention() {
    override def name(name: String, meterType: Meter.Type, baseUnit: String): String = {
      String.format("%s.%s.%s", name, meterType.name(), baseUnit)
    }
  })

  @Bean def objectMapper: ObjectMapper = new ObjectMapper().registerModule(new DefaultScalaModule).registerModule(new ParameterNamesModule).registerModule(new Jdk8Module).registerModule(new JavaTimeModule) // new module, NOT JSR310Module
  @Bean def redisConnectionFactory = new LettuceConnectionFactory(new RedisStandaloneConfiguration("localhost", 6379))

  @Bean private[Ingrid] def stringRedisTemplate(redisConnectionFactory: RedisConnectionFactory) = new StringRedisTemplate(redisConnectionFactory)

  @Bean private[Ingrid] def articleRedisTemplate(redisConnectionFactory: RedisConnectionFactory) = {
    val redisTemplate = new RedisTemplate[java.lang.Long, Article]
    redisTemplate.setConnectionFactory(redisConnectionFactory)
    redisTemplate
  }

  @Bean private[Ingrid] def txEventRedisTemplate(redisConnectionFactory: RedisConnectionFactory) = {
    val redisTemplate = new RedisTemplate[Long, TxEvent]
    redisTemplate.setConnectionFactory(redisConnectionFactory)
    redisTemplate
  }

  /**
    * redis MQ
    */
  @Bean private[Ingrid] def channelTopic = new ChannelTopic("dts")

  @Bean def messageListenerAdapter(txEventConsumer: TxEventConsumer) = new MessageListenerAdapter(txEventConsumer)

  @Bean def redisMessageListenerContainer(redisConnectionFactory: RedisConnectionFactory, messageListenerAdapter: MessageListenerAdapter, channelTopic: ChannelTopic): RedisMessageListenerContainer = {
    val container = new RedisMessageListenerContainer
    container.setConnectionFactory(redisConnectionFactory)
    container.addMessageListener(messageListenerAdapter, channelTopic)
    container
  }

  @Bean
  def destSpecs(destSpecs: java.util.List[TxDestSpec]): List[TxDestSpec] = destSpecs.asScala.toList
//  @Bean def txDispatcher(destSpecs: java.util.List[TxDestSpec]): TxDispatcher = {
//    new TxDispatcher(destSpecs.asScala.toList)
//  }

  @Bean private[Ingrid] def cxService(metrics: MeterRegistry, executors: java.util.List[CxExecutor]) = {
    new CxService(metrics, executors.asScala.toList)
  }
}