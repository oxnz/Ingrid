package io.github.oxnz.Ingrid.dts;

import java.net.http.HttpRequest;

public class HttpTransmitter implements Transmitter<HttpRequest, TxResult> {

    @Override
    public TxResult transmit(HttpRequest request) {
        return new TxResult(true, "succeeded");
    }

}
