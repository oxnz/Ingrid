package io.github.oxnz.Ingrid.dcs;

import io.github.oxnz.Ingrid.dts.TxCategory;

public class CheckinDataCompReq extends CompRequest {
    public CheckinDataCompReq() {
        super(TxCategory.CHECKIN);
    }
}
