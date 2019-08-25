package io.github.oxnz.Ingrid.dts.mq;

import io.github.oxnz.Ingrid.dts.TxRecord;

public class Producer {

    TxEvent transform(TxRecord record) {
        TxEvent event = new TxEvent(record.getId());
        return event;
    }

    void post(TxRecord record) {
        TxEvent event = transform(record);
        System.out.println("pub: " + event.getId());
    }
}
