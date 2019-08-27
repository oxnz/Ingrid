package io.github.oxnz.Ingrid.tx;

import org.apache.http.client.methods.HttpPost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ExecutionException;

@Service
public class TxService implements AutoCloseable {

    final Logger log = LoggerFactory.getLogger(getClass());

    private final TxRecordRepo txRecordRepo;
    private final TxResultRepo txResultRepo;
    private final Dispatcher dispatcher;
    private final HttpExecutionService httpExecutionService;

    public TxService(TxRecordRepo txRecordRepo, TxResultRepo txResultRepo, Dispatcher dispatcher, HttpExecutionService httpExecutionService) {
        this.txRecordRepo = txRecordRepo;
        this.txResultRepo = txResultRepo;
        this.dispatcher = dispatcher;
        this.httpExecutionService = httpExecutionService;
    }

    public void process(long id) throws TxException {
        try {
            TxRecord record = txRecordRepo.findById(id).orElseThrow(NoResultException::new);
            log.debug("record: {}", record);
            // TODO: cxService
            // cxRecord cxRecord = cxService.complete(record.getId());
            // record.data = cxRecord.toString();
            // txRecordRepo.save(record);
            Set<DestSpec> destSpecs = dispatcher.dispatch(record);
            destSpecs.forEach(destSpec -> post(record, destSpec));
        } catch (Exception e) {
            throw new TxException(e);
        }
    }

    private void post(TxRecord record, DestSpec destSpec) {
        HttpExecutionResult httpExecResult = doPost(record, destSpec);
        TxResult txResult = new TxResult(record, httpExecResult.succeeded(), httpExecResult.message());
        txResultRepo.save(txResult);
        log.debug("result: {}", txResult);
    }

    private HttpExecutionResult doPost(TxRecord record, DestSpec destSpec) {
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
