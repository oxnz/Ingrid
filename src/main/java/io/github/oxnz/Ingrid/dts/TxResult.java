package io.github.oxnz.Ingrid.dts;

public class TxResult {
    final boolean succ;
    final String msg;

    public TxResult(boolean succ, String msg) {
        this.succ = succ;
        this.msg = msg;
    }
}
