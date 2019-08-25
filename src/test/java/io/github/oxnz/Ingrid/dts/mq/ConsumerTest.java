package io.github.oxnz.Ingrid.dts.mq;

import io.github.oxnz.Ingrid.dts.Dispatcher;
import io.github.oxnz.Ingrid.dts.TxCategory;
import io.github.oxnz.Ingrid.dts.TxRecord;
import io.github.oxnz.Ingrid.dts.TxService;
import io.github.oxnz.Ingrid.repo.DataLoader;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ConsumerTest {

    @Mock
    DataLoader<TxRecord, Long> dataLoader;
    @Mock
    Dispatcher dispatcher;
    @Mock
    TxService txService;

    @Test
    public void process() throws Exception {
        long id = 123L;
        TxCategory cat = TxCategory.CHECKIN;
        String data = "dat";
        when(dataLoader.fetchById(eq(id))).thenReturn(new TxRecord(id, cat, data));
        when(dispatcher.dispatch(any())).thenReturn(new ArrayList<>());
        try (Consumer consumer = new Consumer(dataLoader, dispatcher, txService, new SimpleMeterRegistry())) {
            TxEvent event = new TxEvent(123L);
            consumer.process(event);
            verify(dataLoader).fetchById(eq(id));
            verify(dispatcher).dispatch(any());
        }
    }
}