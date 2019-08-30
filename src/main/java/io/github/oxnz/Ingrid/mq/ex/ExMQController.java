package io.github.oxnz.Ingrid.mq.ex;

import io.github.oxnz.Ingrid.mq.MQMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("mq/ex")
public class ExMQController {

    private final ExMQRepository mqRepository;

    public ExMQController(ExMQRepository mqRepository) {
        this.mqRepository = mqRepository;
    }

    @GetMapping("msg")
    public MQMessage<ExRecord> get(){
        return mqRepository.deq();
    }

    @PutMapping("msg")
    public ResponseEntity<MQMessage<ExRecord>> put(@RequestBody ExRecord exRecord) {
        return new ResponseEntity<>(mqRepository.enq(exRecord), HttpStatus.CREATED);
    }
}
