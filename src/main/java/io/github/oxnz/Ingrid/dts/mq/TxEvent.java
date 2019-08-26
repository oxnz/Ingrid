package io.github.oxnz.Ingrid.dts.mq;

import java.io.Serializable;

public class TxEvent implements Serializable {
    final long id;

    public TxEvent(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
