package io.github.oxnz.Ingrid.tx;

import io.github.oxnz.Ingrid.tx.mq.RedisMessageProducer;
import io.github.oxnz.Ingrid.tx.mq.TxEvent;
import io.micrometer.core.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("tx")
public class TxController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final TxRecordRepo txRecordRepo;
    private final TxResultRepo txResultRepo;
    private final RedisMessageProducer messageProducer;

    public TxController(TxRecordRepo txRecordRepo, TxResultRepo txResultRepo, RedisMessageProducer messageProducer) {
        this.txRecordRepo = txRecordRepo;
        this.txResultRepo = txResultRepo;
        this.messageProducer = messageProducer;
    }

    @GetMapping("records")
    @Timed
    public ResponseEntity<List<TxRecord>> records() {
        List<TxRecord> entities = txRecordRepo.findAll();
        return new ResponseEntity<>(entities, HttpStatus.OK);
    }

    @GetMapping("results")
    @Timed
    public ResponseEntity<List<TxResult>> txResults() {
        List<TxResult> entities = txResultRepo.findAll();
        return new ResponseEntity<>(entities, HttpStatus.OK);
    }

    @PostMapping("record")
    @Timed(value = "post")
    public ResponseEntity<TxResponse> post(@RequestBody TxRequest request) {
        TxResponse entity = process(request);
        return new ResponseEntity<>(entity, HttpStatus.OK);
    }

    private TxResponse process(TxRequest request) {
        TxRecord record = new TxRecord(request.cat, request.ref, request.state, request.city);
        record = txRecordRepo.save(record);
        TxEvent event = new TxEvent(record.id());
        messageProducer.publish(event);
        return new TxResponse(record.id());
    }
}
