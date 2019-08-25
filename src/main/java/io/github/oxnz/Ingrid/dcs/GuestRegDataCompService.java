package io.github.oxnz.Ingrid.dcs;

import io.github.oxnz.Ingrid.dts.TxRecord;
import io.micrometer.core.instrument.MeterRegistry;

public class GuestRegDataCompService extends CompService<CheckinDataCompReq> {
    public GuestRegDataCompService(MeterRegistry metrics) {
        super(metrics);
    }

    @Override
    public TxRecord process(CheckinDataCompReq request) throws Exception {
        return null;
    }
}
