package io.github.oxnz.Ingrid.mq;

public interface MQRepository<T> {
    MQMessage<T> enq(T msg);
    MQMessage<T> deq();
}
