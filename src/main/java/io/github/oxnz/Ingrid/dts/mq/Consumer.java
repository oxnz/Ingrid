package io.github.oxnz.Ingrid.dts.mq;

import io.github.oxnz.Ingrid.dts.*;
import io.github.oxnz.Ingrid.repo.DataLoader;
import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Consumer implements AutoCloseable {

    final Logger log = LoggerFactory.getLogger(getClass());

    final DataLoader<CxRecord, Long> dataLoader;
    final Dispatcher dispatcher;
    final TxService transfer;
    final MeterRegistry metrics;

    public Consumer(DataLoader<CxRecord, Long> dataLoader, Dispatcher dispatcher, TxService transfer, MeterRegistry metrics) {
        this.dataLoader = dataLoader;
        this.dispatcher = dispatcher;
        this.transfer = transfer;
        this.metrics = metrics;
    }

    void process(TxEvent event) {
        long id = event.getId();
        CxRecord record = dataLoader.fetchById(id);
        process(record);
    }

    void process(CxRecord record) {
        Iterable<DestSpec> destSpecs = null;// = dispatcher.dispatch(record);
        destSpecs.forEach(destSpec -> {
            try {
                process(record, destSpec);
            } catch (TxException e) {
                e.printStackTrace();
                metrics.counter("consumer.process", "failed").increment();
            }
        });
    }

    void process(CxRecord record, DestSpec destSpec) throws TxException {
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
