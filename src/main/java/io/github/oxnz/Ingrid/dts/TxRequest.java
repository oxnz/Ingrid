package io.github.oxnz.Ingrid.dts;

public class TxRequest {
    final String ref;
    final TxCategory cat;

    public TxRequest(String ref, TxCategory cat) {
        this.ref = ref;
        this.cat = cat;
    }
}
