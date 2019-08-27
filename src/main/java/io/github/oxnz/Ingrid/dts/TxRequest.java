package io.github.oxnz.Ingrid.dts;

public class TxRequest {
    final TxCategory cat;
    final String ref;
    final String state;
    final String city;

    public TxRequest(String ref, TxCategory cat, String state, String city) {
        this.ref = ref;
        this.cat = cat;
        this.state = state;
        this.city = city;
    }
}
