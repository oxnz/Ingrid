package io.github.oxnz.Ingrid.mq.ex;

import io.github.oxnz.Ingrid.mq.MQChannel;
import io.github.oxnz.Ingrid.mq.MQMessage;
import io.github.oxnz.Ingrid.mq.MQProducer;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

@MQChannel(topic = "ex", brokers = "http://localhost:8080/ex/mq/msg")
public class ExMQProducer implements MQProducer<ExRecord> {

    private final RestTemplate restTemplate;

    public ExMQProducer(RestTemplate restTemplate) {
        Class<? extends ExMQProducer> clazz = getClass();
        Assert.isTrue(clazz.isAnnotationPresent(MQChannel.class), "MQChannel required");
        MQChannel channel = getClass().getAnnotation(MQChannel.class);
        this.restTemplate = restTemplate;
    }

    @Override
    public MQMessage<ExRecord> pub(ExRecord msg) {
        return null;
    }
}
