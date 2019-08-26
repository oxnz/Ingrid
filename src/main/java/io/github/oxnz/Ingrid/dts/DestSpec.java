package io.github.oxnz.Ingrid.dts;

import org.apache.http.client.ResponseHandler;

public interface DestSpec {

    ResponseHandler<? extends HttpExecutionResult> responseHandler();
    RequestBuilder requestBuilder();

}
