package io.github.oxnz.Ingrid.tx;

import org.apache.http.client.methods.HttpPost;

public interface RequestBuilder {

    HttpPost buildRequest(TxRecord record, DestSpec destSpec);

}
