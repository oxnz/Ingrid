package io.github.oxnz.Ingrid.dts;

import io.github.oxnz.Ingrid.dts.data.TxDataRepo;
import io.github.oxnz.Ingrid.dts.data.TxRecord;
import org.apache.http.client.methods.HttpPost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class TxService implements AutoCloseable {

    final Logger log = LoggerFactory.getLogger(getClass());

    private final TxDataRepo txDataRepo;
    private final TxResultRepo txResultRepo;
    private final Dispatcher dispatcher;
    private final HttpExecutionService httpExecutionService;

    public TxService(TxDataRepo txDataRepo, TxResultRepo txResultRepo, Dispatcher dispatcher, HttpExecutionService httpExecutionService) {
        this.txDataRepo = txDataRepo;
        this.txResultRepo = txResultRepo;
        this.dispatcher = dispatcher;
        this.httpExecutionService = httpExecutionService;
    }

    public void process(long id) throws TxException {
        try {
            log.debug("processing {}", id);
            TxRecord record = txDataRepo.findById(id).orElseThrow(NoResultException::new);
            log.debug("record: {}", record);
            List<DestSpec> destSpecs = dispatcher.dispatch(record);
            for (DestSpec destSpec : destSpecs) {
                HttpExecutionResult httpExecResult = post(record, destSpec);
                TxResult txResult = new TxResult(record, httpExecResult.succeeded(), httpExecResult.message());
                log.debug("tx: {} {}", record, txResult);
                txResultRepo.save(txResult);
            }
        } catch (Exception e) {
            throw new TxException(e);
        }
    }

    public HttpExecutionResult post(TxRecord record, DestSpec destSpec) {
        HttpPost request = destSpec.requestBuilder().buildRequest(record, destSpec);
        try {
            return httpExecutionService.execute(request, null, destSpec.responseHandler());
        } catch (InterruptedException | ExecutionException e) {
            log.error("post", e);
            return new HttpExecutionResult(false, e.getMessage());
        }
    }

    @Override
    public void close() throws IOException {
        httpExecutionService.close();
    }
}
