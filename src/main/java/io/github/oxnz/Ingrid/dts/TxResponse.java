package io.github.oxnz.Ingrid.dts;

public class TxResponse {
    public long getId() {
        return id;
    }

    final long id;

    public TxResponse(long id) {
        this.id = id;
    }
}
