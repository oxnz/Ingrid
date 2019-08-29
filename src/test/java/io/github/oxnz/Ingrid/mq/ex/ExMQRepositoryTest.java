package io.github.oxnz.Ingrid.mq.ex;

import io.github.oxnz.Ingrid.mq.MQMessage;
import org.junit.Test;

import java.util.Random;

public class ExMQRepositoryTest {

    private ExMQRepository exMQRepository = new ExMQRepository();
    private Random random = new Random(System.currentTimeMillis());

    @Test
    public void enq() {
    }

    @Test
    public void deq() {
        ExRecord record = new ExRecord(random.nextInt());
        MQMessage<ExRecord> msg = exMQRepository.enq(record);
        System.out.println(msg);
        msg = exMQRepository.deq();
        System.out.println(msg);
    }
}