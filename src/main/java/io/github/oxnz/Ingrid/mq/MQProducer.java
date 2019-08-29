package io.github.oxnz.Ingrid.mq;

@FunctionalInterface
public interface MQProducer<T> {
    MQMessage<T> pub(T msg);
}
