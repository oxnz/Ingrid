package io.github.oxnz.Ingrid.mq.rabbit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;

@Component
@RabbitListener(queues = "rabbit")
public class RabbitConsumer implements MessageListener {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final AtomicLong counter = new AtomicLong(0);
    @Override
    public void onMessage(Message message) {
//        log.info("recved: {}", message);
    }

    @RabbitHandler
    public void receive(String in) {
//        log.info("recved: {}", in);
        counter.incrementAndGet();
    }

    public long count() {
        log.info("count: {}", counter.get());
        return counter.get();
    }
}
