package io.github.oxnz.Ingrid.dts.mq;

import io.github.oxnz.Ingrid.dts.TxRecord;
import io.github.oxnz.Ingrid.dts.data.TxDataRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Producer implements AutoCloseable {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final TxDataRepo txDataRepo;

    public Producer(TxDataRepo txDataRepo) {
        this.txDataRepo = txDataRepo;
    }

    TxEvent transform(TxRecord record) {
        TxEvent event = new TxEvent(record.getId());
        return event;
    }

    void post(TxRecord record) {
        record = txDataRepo.save(record);
        TxEvent event = transform(record);
        System.out.println("pub: " + event.getId());
    }

    @Override
    public void close() throws Exception {
        log.info("closing ...");
        log.info("closed");
    }
}
