package io.github.oxnz.Ingrid.dts.mq;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

@Service
public class RedisMessageConsumer implements MessageListener {

    @Override
    public void onMessage(Message message, byte[] pattern) {
    }
}
