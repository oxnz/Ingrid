package io.github.oxnz.Ingrid.dcs;

import io.github.oxnz.Ingrid.tx.CxRecord;
import io.micrometer.core.instrument.MeterRegistry;

public class GuestCxService extends CxService<CheckinDataCxReq> {
    public GuestCxService(MeterRegistry metrics) {
        super(metrics);
    }

    @Override
    public CxRecord process(CheckinDataCxReq request) throws Exception {
        return null;
    }
}
