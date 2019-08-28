package io.github.oxnz.Ingrid.tx

import org.slf4j.LoggerFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.listener.ChannelTopic
import org.springframework.stereotype.Service

@Service
class TxEventProducer(val redisTemplate: RedisTemplate[Long, TxEvent], val channelTopic: ChannelTopic) {
  final private val log = LoggerFactory.getLogger(getClass)

  def publish(message: TxEvent): Unit = redisTemplate.convertAndSend(channelTopic.getTopic, message)
}