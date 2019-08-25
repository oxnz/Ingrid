package io.github.oxnz.Ingrid.dts;

import org.apache.http.client.methods.HttpPost;

public interface RequestBuilder {

    HttpPost buildRequest(TxRecord record, DestSpec destSpec);

}
