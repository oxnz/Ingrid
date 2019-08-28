package io.github.oxnz.Ingrid.cx;


import io.github.oxnz.Ingrid.tx.TxCategory;

public class CheckinDataCxReq extends CxRequest {
    public CheckinDataCxReq() {
        super(1, TxCategory.CHECKIN, "HELLO");
    }
}
