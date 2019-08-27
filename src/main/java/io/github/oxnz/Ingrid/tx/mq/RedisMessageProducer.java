package io.github.oxnz.Ingrid.tx.mq;

import io.github.oxnz.Ingrid.tx.TxEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@Service
public class RedisMessageProducer {

    private final Logger log = LoggerFactory.getLogger(getClass());

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
