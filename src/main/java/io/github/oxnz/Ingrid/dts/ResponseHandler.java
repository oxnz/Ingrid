package io.github.oxnz.Ingrid.dts;

import java.net.http.HttpResponse;

public interface ResponseHandler {
    TxResult handleResponse(HttpResponse<String> response);
}
