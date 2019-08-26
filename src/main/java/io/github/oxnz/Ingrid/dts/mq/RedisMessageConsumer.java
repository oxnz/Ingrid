package io.github.oxnz.Ingrid.dts.mq;

import io.github.oxnz.Ingrid.dts.DestSpec;
import io.github.oxnz.Ingrid.dts.TxException;
import io.github.oxnz.Ingrid.dts.TxResult;
import io.github.oxnz.Ingrid.dts.TxService;
import io.github.oxnz.Ingrid.dts.data.TxDataRepo;
import io.github.oxnz.Ingrid.dts.data.TxRecord;
import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.util.List;

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
            txService.process(event.id);
        } catch (IOException | ClassNotFoundException | TxException e) {
            throw new RuntimeException(e);
        }
    }

}