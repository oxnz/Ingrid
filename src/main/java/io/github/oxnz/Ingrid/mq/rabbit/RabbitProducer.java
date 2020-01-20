package io.github.oxnz.Ingrid.mq.rabbit;

import io.github.oxnz.Ingrid.mq.MQMessage;
import io.github.oxnz.Ingrid.mq.MQProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class RabbitProducer implements MQProducer<String> {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final RabbitTemplate template;
    private final Queue queue;

    public RabbitProducer(RabbitTemplate template, Queue queue) {
        this.template = template;
        this.queue = queue;
    }

    @Override
    public MQMessage<String> pub(String msg) {
//        log.info("sending");
        template.convertAndSend(queue.getName(), msg);
//        log.info("sent");
        return null;
    }
}
