package io.github.oxnz.Ingrid.tx;

import org.apache.http.client.ResponseHandler;

public interface DestSpec {

    boolean isInterested(TxCategory cat);
    ResponseHandler<? extends TxHttpExecResult> responseHandler();
    TxHttpReqBuilder requestBuilder();

}
