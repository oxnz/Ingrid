package io.github.oxnz.Ingrid.dcs;

import io.github.oxnz.Ingrid.tx.CxRecord;
import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class CxService<T> {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final MeterRegistry metrics;

    CxService(MeterRegistry metrics) {
        this.metrics = metrics;
    }

    public abstract CxRecord process(T request) throws Exception;

    public CxRecord complete(T request) throws CxException {
        boolean succ = true;
        try {
            return process(request);
        } catch (Exception e) {
            succ = false;
            log.error("comp", e);
            throw new CxException(e);
        } finally {
            metrics.counter(MetricsNames.Counter, String.valueOf(succ)).increment();
        }
    }

    private interface MetricsNames {
        String Counter = "dts.dcs.comp";
    }

}
