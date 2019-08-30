package io.github.oxnz.Ingrid.mq.ex;

import io.github.oxnz.Ingrid.mq.MQMessage;
import io.github.oxnz.Ingrid.mq.MQRepository;
import org.springframework.stereotype.Repository;

import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ExMQRepository implements MQRepository<ExRecord> {
    private final AtomicLong snCounter = new AtomicLong(1);
    private final Semaphore msgAvaliable = new Semaphore(0);
    private final Deque<MQMessage<ExRecord>> mq = new ConcurrentLinkedDeque<>();

    private final int capacity;
    private final Semaphore memAvaliable;

    public ExMQRepository() {
        this(100);
    }

    public ExMQRepository(int capacity) {
        this.capacity = capacity;
        memAvaliable = new Semaphore(capacity);
    }

    private MQMessage<ExRecord> push(ExRecord exRecord) throws InterruptedException {
        memAvaliable.acquire();
        MQMessage<ExRecord> msg = new MQMessage<>(snCounter.getAndIncrement(), exRecord);
        mq.push(msg);
        msgAvaliable.release();
        System.out.println("produce: " + msg);
        return msg;
    }

    private MQMessage<ExRecord> poll() throws InterruptedException {
        msgAvaliable.acquire();
        MQMessage<ExRecord> msg = mq.poll();
        memAvaliable.release();
        System.out.println("consume: " + msg);
        return msg;
    }

    @Override
    public MQMessage<ExRecord> enq(ExRecord exRecord) {
        try {
            return push(exRecord);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public MQMessage<ExRecord> deq() {
        try {
            return poll();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
