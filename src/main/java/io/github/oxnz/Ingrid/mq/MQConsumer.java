package io.github.oxnz.Ingrid.mq;

@FunctionalInterface
public interface MQConsumer<T> {
    void process(MQMessage<T> msg);
}
