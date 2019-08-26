package io.github.oxnz.Ingrid.dts;

import org.apache.http.client.ResponseHandler;

public class DestSpec {

    public final ResponseHandler<? extends HttpExecutionResult> responseHandler;
    public final RequestBuilder requestBuilder;

    public DestSpec(ResponseHandler<? extends HttpExecutionResult> responseHandler, RequestBuilder requestBuilder) {
        this.responseHandler = responseHandler;
        this.requestBuilder = requestBuilder;
    }
}
