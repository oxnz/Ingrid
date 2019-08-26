package io.github.oxnz.Ingrid.dts;

import io.github.oxnz.Ingrid.dts.data.TxDataRepo;
import io.github.oxnz.Ingrid.dts.data.TxRecord;
import io.github.oxnz.Ingrid.dts.mq.RedisMessageProducer;
import io.github.oxnz.Ingrid.dts.mq.TxEvent;
import io.micrometer.core.annotation.Timed;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("tx")
public class TxController {

    private final TxDataRepo txDataRepo;
    private final RedisMessageProducer messageProducer;

    public TxController(TxDataRepo txDataRepo, RedisMessageProducer messageProducer) {
        this.txDataRepo = txDataRepo;
        this.messageProducer = messageProducer;
    }

    @RequestMapping("")
    @Timed(value = "index")
    public String index() {
        return "dts";
    }

    @PostMapping("{cat}/{id}")
    @Timed(value = "post")
    public ResponseEntity<TxResponse> post(@RequestBody TxRequest request) {
        TxResponse entity = process(request);
        return new ResponseEntity<>(entity, HttpStatus.OK);
    }

    private TxResponse process(TxRequest request) {
        TxRecord record = new TxRecord(request.cat, request.ref);
        record = txDataRepo.save(record);
        TxEvent event = new TxEvent(record.getId());
        messageProducer.publish(event);
        return new TxResponse(record.getId());
    }
}
