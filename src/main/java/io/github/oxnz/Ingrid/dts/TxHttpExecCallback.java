package io.github.oxnz.Ingrid.dts;

import org.apache.http.concurrent.FutureCallback;

public class TxHttpExecCallback implements FutureCallback<TxResult> {
    @Override
    public void completed(TxResult result) {

    }

    @Override
    public void failed(Exception ex) {

    }

    @Override
    public final void cancelled() {
        // no-op
    }
}
