package io.github.oxnz.Ingrid.dts.mq;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@Service
public class RedisMessageProducer {
    private final RedisTemplate<Long, TxEvent> redisTemplate;
    private final ChannelTopic channelTopic;

    public RedisMessageProducer(RedisTemplate<Long, TxEvent> redisTemplate, ChannelTopic channelTopic) {
        this.redisTemplate = redisTemplate;
        this.channelTopic = channelTopic;
    }

    public void publish(TxEvent message) {
        redisTemplate.convertAndSend(channelTopic.getTopic(), message);
    }

}
