package io.github.oxnz.Ingrid.dts.mq;

public class TxEvent {
    public long getId() {
        return id;
    }

    final long id;

    public TxEvent(long id) {
        this.id = id;
    }
}
