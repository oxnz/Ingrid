package io.github.oxnz.Ingrid.dts.mq;

import io.github.oxnz.Ingrid.dts.data.TxDataRepo;
import org.junit.Test;
import org.mockito.Mock;

public class ProducerTest {

    @Mock
    TxDataRepo txDataRepo;

    @Test
    public void post() throws Exception {
        try (Producer producer = new Producer(txDataRepo)) {
        }
    }
}