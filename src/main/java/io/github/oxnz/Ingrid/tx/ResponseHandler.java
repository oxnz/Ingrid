package io.github.oxnz.Ingrid.tx;

import org.apache.http.HttpResponse;

public interface ResponseHandler {
    TxResult handleResponse(HttpResponse response);
}
