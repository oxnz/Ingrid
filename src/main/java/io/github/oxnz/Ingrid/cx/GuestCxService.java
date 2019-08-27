package io.github.oxnz.Ingrid.cx;

import io.micrometer.core.instrument.MeterRegistry;

public class GuestCxService extends CxService<CheckinDataCxReq> {
    public GuestCxService(MeterRegistry metrics) {
        super(metrics);
    }

    @Override
    public CxRequest process(CheckinDataCxReq request) throws Exception {
        return null;
    }
}
