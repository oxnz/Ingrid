package io.github.oxnz.Ingrid.dts;

import io.github.oxnz.Ingrid.dts.data.TxDataRepo;
import io.github.oxnz.Ingrid.dts.data.TxRecord;
import io.github.oxnz.Ingrid.dts.mq.RedisMessageProducer;
import io.github.oxnz.Ingrid.dts.mq.TxEvent;
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

    private final TxDataRepo txDataRepo;
    private final TxResultRepo txResultRepo;
    private final RedisMessageProducer messageProducer;

    public TxController(TxDataRepo txDataRepo, TxResultRepo txResultRepo, RedisMessageProducer messageProducer) {
        this.txDataRepo = txDataRepo;
        this.txResultRepo = txResultRepo;
        this.messageProducer = messageProducer;
    }

    @GetMapping("records")
    @Timed
    public ResponseEntity<List<TxRecord>> records() {
        List<TxRecord> entities = txDataRepo.findAll();
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
        record = txDataRepo.save(record);
        TxEvent event = new TxEvent(record.getId());
        messageProducer.publish(event);
        return new TxResponse(record.getId());
    }
}
