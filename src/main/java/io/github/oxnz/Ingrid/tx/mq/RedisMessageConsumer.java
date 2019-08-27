package io.github.oxnz.Ingrid.tx.mq;

import io.github.oxnz.Ingrid.tx.TxEvent;
import io.github.oxnz.Ingrid.tx.TxException;
import io.github.oxnz.Ingrid.tx.TxService;
import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;

@Service
public class RedisMessageConsumer implements MessageListener {

    final Logger log = LoggerFactory.getLogger(getClass());

    private final MeterRegistry metrics;
    private final TxService txService;

    public RedisMessageConsumer(MeterRegistry metrics, TxService txService) {
        this.metrics = metrics;
        this.txService = txService;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(message.getBody());
             ObjectInput in = new ObjectInputStream(bis)) {
            TxEvent event = (TxEvent) in.readObject();
            log.debug("msg: {}", event);
            txService.process(event.id());
        } catch (IOException | ClassNotFoundException | TxException e) {
            throw new RuntimeException(e);
        }
    }

}
