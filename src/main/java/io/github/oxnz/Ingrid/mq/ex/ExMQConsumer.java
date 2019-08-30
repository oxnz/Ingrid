package io.github.oxnz.Ingrid.mq.ex;

import io.github.oxnz.Ingrid.mq.MQChannel;
import io.github.oxnz.Ingrid.mq.MQConsumer;
import io.github.oxnz.Ingrid.mq.MQMessage;

@MQChannel(topic = "ex", brokers = "http://localhost:8080/ex/mq/msg")
public class ExMQConsumer implements MQConsumer<ExRecord> {
    @Override
    public void process(MQMessage<ExRecord> msg) {
        System.out.println(msg);
    }
}
