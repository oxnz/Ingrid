package io.github.oxnz.Ingrid.dts;

import java.net.http.HttpRequest;

public class TxService {

    private final HttpTransmitter transmitter;

    public TxService(HttpTransmitter transmitter) {
        this.transmitter = transmitter;
    }

    public TxResult transmit(TxRecord record, DestSpec destSpec) {
        HttpRequest request = destSpec.requestBuilder.buildRequest(record);
        return transmitter.transmit(request);
    }

}
