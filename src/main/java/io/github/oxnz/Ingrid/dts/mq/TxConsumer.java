package io.github.oxnz.Ingrid.dts.mq;

import io.github.oxnz.Ingrid.dts.*;
import io.github.oxnz.Ingrid.repo.DataLoader;

public class TxConsumer {

    final DataLoader<TxRecord, Long> dataLoader;
    final Dispatcher dispatcher;
    final TxService service;

    public TxConsumer(DataLoader<TxRecord, Long> dataLoader, Dispatcher dispatcher, TxService service) {
        this.dataLoader = dataLoader;
        this.dispatcher = dispatcher;
        this.service = service;
    }

    void process(TxEvent event) {
        long id = event.getId();
        TxRecord record = dataLoader.fetchById(id);
        process(record);
    }


    void process(TxRecord record) {
        Iterable<DestSpec> destSpecs = dispatcher.dispatch(record);
        destSpecs.forEach(destSpec -> process(record, destSpec));
    }

    void process(TxRecord record, DestSpec destSpec) {
        TxResult result = service.transmit(record, destSpec);
        System.out.println(result);
    }

}
