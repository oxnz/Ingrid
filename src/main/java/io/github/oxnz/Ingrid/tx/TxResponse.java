package io.github.oxnz.Ingrid.tx;

public class TxResponse {
    final long id;

    public TxResponse(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
