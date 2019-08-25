package io.github.oxnz.Ingrid.dcs;

import io.github.oxnz.Ingrid.dts.TxCategory;

public class GuestRegDataCompReq extends CompRequest {
    public GuestRegDataCompReq() {
        super(TxCategory.GUEST_REG);
    }
}
