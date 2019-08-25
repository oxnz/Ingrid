package io.github.oxnz.Ingrid.dts;

import org.apache.http.client.ResponseHandler;

public class DestSpec {

    public final ResponseHandler<? extends TxResult> responseHandler;
    public final RequestBuilder requestBuilder;

    public DestSpec(ResponseHandler<? extends TxResult> responseHandler, RequestBuilder requestBuilder) {
        this.responseHandler = responseHandler;
        this.requestBuilder = requestBuilder;
    }
}
