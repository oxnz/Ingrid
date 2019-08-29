package io.github.oxnz.Ingrid.mq;

public class MQMessage<T> {
    public final long sn;
    public final T body;

    public MQMessage(long sn, T body) {
        this.sn = sn;
        this.body = body;
    }

    @Override
    public String toString() {
        return "MQMessage{" +
                "sn=" + sn +
                ", body=" + body +
                '}';
    }
}
