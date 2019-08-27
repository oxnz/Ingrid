package io.github.oxnz.Ingrid.tx;

import io.github.oxnz.Ingrid.tx.mq.RedisMessageProducer;
import io.github.oxnz.Ingrid.tx.mq.TxEvent;
import io.micrometer.core.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Timed
@Controller
@RequestMapping("admin/tx")
public class TxAdminController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final TxRecordRepo txRecordRepo;
    private final TxResultRepo txResultRepo;
    private final RedisMessageProducer messageProducer;

    public TxAdminController(TxRecordRepo txRecordRepo, TxResultRepo txResultRepo, RedisMessageProducer messageProducer) {
        this.txRecordRepo = txRecordRepo;
        this.txResultRepo = txResultRepo;
        this.messageProducer = messageProducer;
    }

    @GetMapping("")
    public String index() {
        return "admin/tx/index";
    }

    @PostMapping("")
    public String post(@RequestBody TxRequest request) {
        TxResponse entity = process(request);
        return "index";
    }

    @GetMapping("records")
    public String txRecords(Model model) {
        List<TxRecord> records = txRecordRepo.findAll();
        model.addAttribute("records", records);
        return "admin/tx/records";
    }

    @GetMapping("results")
    public String txResults(Model model) {
        List<TxResult> results = txResultRepo.findAll();
        model.addAttribute("results", results);
        return "admin/tx/results";
    }

    private TxResponse process(TxRequest request) {
        TxRecord record = new TxRecord(request.cat, request.ref, request.state, request.city);
        record = txRecordRepo.save(record);
        TxEvent event = new TxEvent(record.id());
        messageProducer.publish(event);
        return new TxResponse(record.id());
    }
}
