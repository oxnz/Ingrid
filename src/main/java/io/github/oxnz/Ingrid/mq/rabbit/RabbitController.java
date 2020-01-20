package io.github.oxnz.Ingrid.mq.rabbit;

import io.github.oxnz.Ingrid.mq.MQMessage;
import io.github.oxnz.Ingrid.mq.ex.ExRecord;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("mq/rabbit")
public class RabbitController {

    private final RabbitProducer producer;
    private final RabbitConsumer consumer;

    public RabbitController(RabbitProducer producer, RabbitConsumer consumer) {
        this.producer = producer;
        this.consumer = consumer;
    }

    @GetMapping("count")
    public long count() {
        return consumer.count();
    }

    @PutMapping("msg")
    public String put() {
        String message = "hello";
        producer.pub(message.toString());
        return "message";
    }

    @PutMapping("msgx")
    public String putx(@RequestBody MQMessage<ExRecord> message) {
        producer.pub(message.toString());
        return "message";
    }
}
