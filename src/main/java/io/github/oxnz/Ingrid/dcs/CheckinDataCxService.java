package io.github.oxnz.Ingrid.dcs;

import io.github.oxnz.Ingrid.dts.CxRecord;
import io.micrometer.core.instrument.MeterRegistry;

public class CheckinDataCxService extends CxService<CheckinDataCxReq> {

    public CheckinDataCxService(MeterRegistry metrics) {
        super(metrics);
    }

    @Override
    public CxRecord process(CheckinDataCxReq request) throws Exception {
        return null;
    }

}
