package io.github.oxnz.Ingrid.dts;

import java.net.http.HttpRequest;

public interface RequestBuilder {

    HttpRequest buildRequest(TxRecord record);

}
