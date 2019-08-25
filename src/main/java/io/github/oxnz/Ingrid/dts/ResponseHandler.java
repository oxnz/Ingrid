package io.github.oxnz.Ingrid.dts;

import org.apache.http.HttpResponse;

public interface ResponseHandler {
    TxResult handleResponse(HttpResponse response);
}
