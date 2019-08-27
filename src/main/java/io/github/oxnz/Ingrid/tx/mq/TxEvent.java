package io.github.oxnz.Ingrid.tx.mq;

import org.springframework.core.style.ToStringCreator;

import java.io.Serializable;

public class TxEvent implements Serializable {
    final long id;

    public TxEvent(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return new ToStringCreator(this).append("id", id).toString();
    }
}
