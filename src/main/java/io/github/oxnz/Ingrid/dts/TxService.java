package io.github.oxnz.Ingrid.dts;

import org.apache.http.client.methods.HttpPost;

import java.io.IOException;

public class TxService implements AutoCloseable {

    private final HttpExecutionService httpExecutionService;

    public TxService(HttpExecutionService httpExecutionService) {
        this.httpExecutionService = httpExecutionService;

    }

    public TxResult post(TxRecord record, DestSpec destSpec) throws TxException {
        HttpPost request = destSpec.requestBuilder.buildRequest(record, destSpec);
        try {
            return httpExecutionService.execute(request, destSpec.responseHandler);
        } catch (IOException e) {
            throw new TxException(e);
        }
    }

    @Override
    public void close() throws IOException {
        httpExecutionService.close();
    }
}
