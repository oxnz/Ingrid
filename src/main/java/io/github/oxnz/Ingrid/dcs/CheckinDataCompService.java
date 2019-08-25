package io.github.oxnz.Ingrid.dcs;

import io.github.oxnz.Ingrid.dts.TxRecord;
import io.micrometer.core.instrument.MeterRegistry;

public class CheckinDataCompService extends CompService<CheckinDataCompReq> {

    public CheckinDataCompService(MeterRegistry metrics) {
        super(metrics);
    }

    @Override
    public TxRecord process(CheckinDataCompReq request) throws Exception {
        return null;
    }

}
