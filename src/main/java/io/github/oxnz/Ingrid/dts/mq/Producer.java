package io.github.oxnz.Ingrid.dts.mq;

import io.github.oxnz.Ingrid.dts.CxRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Producer implements AutoCloseable {

    private final Logger log = LoggerFactory.getLogger(getClass());

    TxEvent transform(CxRecord record) {
        TxEvent event = new TxEvent(record.getId());
        return event;
    }

    void post(CxRecord record) {
        TxEvent event = transform(record);
        System.out.println("pub: " + event.getId());
    }

    @Override
    public void close() throws Exception {
        log.info("closing ...");
        log.info("closed");
    }
}
