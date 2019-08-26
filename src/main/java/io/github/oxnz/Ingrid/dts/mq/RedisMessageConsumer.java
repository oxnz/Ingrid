package io.github.oxnz.Ingrid.dts.mq;

import io.github.oxnz.Ingrid.dts.DestSpec;
import io.github.oxnz.Ingrid.dts.Dispatcher;
import io.github.oxnz.Ingrid.dts.data.TxDataRepo;
import io.github.oxnz.Ingrid.dts.data.TxRecord;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;

@Service
public class RedisMessageConsumer implements MessageListener {

    private final TxDataRepo txDataRepo;
    private final Dispatcher dispatcher;

    public RedisMessageConsumer(TxDataRepo txDataRepo, Dispatcher dispatcher) {
        this.txDataRepo = txDataRepo;
        this.dispatcher = dispatcher;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(message.getBody());
             ObjectInput in = new ObjectInputStream(bis)) {
            TxEvent event = (TxEvent) in.readObject();
            process(event);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void process(TxEvent event) {
        System.out.println(event);
        TxRecord record = txDataRepo.findById(event.id).orElseThrow(NoResultException::new);
        Iterable<DestSpec> destSpecs = dispatcher.dispatch(record);
        System.out.println(destSpecs);
    }
}
