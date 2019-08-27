package io.github.oxnz.Ingrid.cx;

import io.micrometer.core.instrument.MeterRegistry;

public class CheckinDataCxService extends CxService<CheckinDataCxReq> {

    public CheckinDataCxService(MeterRegistry metrics) {
        super(metrics);
    }

    @Override
    public CxRequest process(CheckinDataCxReq request) throws Exception {
        return null;
    }

}
