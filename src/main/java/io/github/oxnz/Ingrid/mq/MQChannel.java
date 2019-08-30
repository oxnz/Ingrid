package io.github.oxnz.Ingrid.mq;

public @interface MQChannel {
    String[] topic();
    String[] brokers();
}
