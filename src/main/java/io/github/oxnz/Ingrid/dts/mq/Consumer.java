package io.github.oxnz.Ingrid.dts.mq;

import io.github.oxnz.Ingrid.dts.*;
import io.github.oxnz.Ingrid.repo.DataLoader;
import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Consumer implements AutoCloseable {

    final Logger log = LoggerFactory.getLogger(getClass());

    final DataLoader<TxRecord, Long> dataLoader;
    final Dispatcher dispatcher;
    final TxService transfer;
    final MeterRegistry metrics;

    public Consumer(DataLoader<TxRecord, Long> dataLoader, Dispatcher dispatcher, TxService transfer, MeterRegistry metrics) {
        this.dataLoader = dataLoader;
        this.dispatcher = dispatcher;
        this.transfer = transfer;
        this.metrics = metrics;
    }

    void process(TxEvent event) {
        long id = event.getId();
        TxRecord record = dataLoader.fetchById(id);
        process(record);
    }

    void process(TxRecord record) {
        Iterable<DestSpec> destSpecs = dispatcher.dispatch(record);
        destSpecs.forEach(destSpec -> {
            try {
                process(record, destSpec);
            } catch (TxException e) {
                e.printStackTrace();
                metrics.counter("consumer.process", "failed").increment();
            }
        });
    }

    void process(TxRecord record, DestSpec destSpec) throws TxException {
        TxResult result = transfer.post(record, destSpec);
        System.out.println(result);
    }

    @Override
    public void close() throws Exception {
        log.info("closing ...");
        transfer.close();
        log.info("closed");
    }
}
