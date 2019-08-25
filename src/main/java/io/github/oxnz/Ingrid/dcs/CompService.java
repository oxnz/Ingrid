package io.github.oxnz.Ingrid.dcs;

import io.github.oxnz.Ingrid.dts.TxRecord;
import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class CompService<T> {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final MeterRegistry metrics;

    private interface MetricsNames {
        String Counter = "dts.dcs.comp";
    }

    CompService(MeterRegistry metrics) {
        this.metrics = metrics;
    }

    public abstract TxRecord process(T request) throws Exception;

    public TxRecord complete(T request) throws DataCompException {
        boolean succ = true;
        try {
            return process(request);
        } catch (Exception e) {
            succ = false;
            log.error("comp", e);
            throw new DataCompException(e);
        } finally {
            metrics.counter(MetricsNames.Counter, String.valueOf(succ)).increment();
        }
    }

}
