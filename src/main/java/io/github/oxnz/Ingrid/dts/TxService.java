package io.github.oxnz.Ingrid.dts;

import org.apache.http.client.methods.HttpPost;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class TxService implements AutoCloseable {

    private final HttpExecutionService httpExecutionService;

    public TxService(HttpExecutionService httpExecutionService) {
        this.httpExecutionService = httpExecutionService;

    }

    public TxResult post(CxRecord record, DestSpec destSpec) throws TxException {
        HttpPost request = destSpec.requestBuilder.buildRequest(record, destSpec);
        try {
            return httpExecutionService.execute(request, null, destSpec.responseHandler);
        } catch (InterruptedException | ExecutionException e) {
            throw new TxException(e);
        }
    }

    @Override
    public void close() throws IOException {
        httpExecutionService.close();
    }
}
