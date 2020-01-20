package io.github.oxnz.Ingrid.cx;

import org.junit.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import static org.junit.Assert.*;

public class CxFieldAccessorTest {

    @Test
    public void opt() {
        CxFieldAccessor accessor = new CxFieldAccessor();
        accessor.put(CxField.ID, CxDataSource.INTERNAL, 1234L, -1L);
        assertEquals(1234L, accessor.optValue(CxField.ID).get().value());
        assertTrue(accessor.optValue(CxField.NAME).isEmpty());
    }

    @Test
    public void has() {
        CxFieldAccessor accessor = new CxFieldAccessor();
        assertFalse(accessor.has(CxField.NAME));
        accessor.put(CxField.NAME, CxDataSource.INTERNAL, "LILI");
        assertTrue(accessor.has(CxField.NAME));
    }

    @Test
    public void value() {
        CxFieldAccessor accessor = new CxFieldAccessor();
        assertTrue(accessor.value(CxField.NAME).isEmpty());
        accessor.put(CxField.NAME, CxDataSource.INTERNAL, "LILI");
        assertEquals("LILI", accessor.value(CxField.NAME).get());
    }

    @Test
    public void testToString() {
    }

    @Test
    public void defaultValue() {
        CxFieldAccessor accessor = new CxFieldAccessor();
        assertTrue(accessor.value(CxField.NAME).isEmpty());
        accessor.put(CxField.NAME, CxDataSource.INTERNAL, null, "CHEN");
        assertEquals("CHEN", accessor.defaultValue(CxField.NAME).get());
    }

    @Test
    public void orDefault() {
    }

    @Test
    public void valueOrDefault() {
        CxFieldAccessor accessor = new CxFieldAccessor();
        assertTrue(accessor.value(CxField.NAME).isEmpty());
        accessor.put(CxField.NAME, CxDataSource.INTERNAL, null, "CHEN");
        assertEquals("CHEN", accessor.valueOrDefault(CxField.NAME));
        assertEquals("LILI", accessor.valueOrDefault(CxField.NAME, "LILI"));
        accessor.put(CxField.ID, CxDataSource.INTERNAL, 1234L, -1L);
        assertEquals(Long.valueOf(1234L), accessor.valueOrDefault(CxField.ID));
        assertEquals(Long.valueOf(1234L), accessor.valueOrDefault(CxField.ID, -9999L));
    }

    @Test
    public void accessors() {
        LocalDateTime now = LocalDateTime.now(Clock.fixed(Instant.now(), ZoneId.systemDefault()));
        String record = new CxFieldAccessor()
                .put(CxField.SUBJECTS, List.of(new CxFieldAccessor()
                                .put(CxField.NAME, CxDataSource.INTERNAL, null, "CHEN")
                                .put(CxField.ADDRESS, new CxFieldAccessor()
                                        .put(CxField.STATE, CxDataSource.EXTERNAL, "California", null)
                                        .put(CxField.CITY, CxDataSource.EXTERNAL, "Los Angles", null)
                                        .put(CxField.CREATED_AT, CxDataSource.EXTERNAL, now)
                                ),
                        new CxFieldAccessor()
                                .put(CxField.NAME, CxDataSource.INTERNAL, "LILI", null)
                )).toString();
        scala.collection.immutable.List<CxFieldAccessor> accessors = CxFieldAccessor.apply(record).accessors(CxField.SUBJECTS);
        assertEquals(2, accessors.size());
        assertEquals("CHEN", accessors.head().valueOrDefault(CxField.NAME));
        assertEquals("California", accessors.head().accessor(CxField.ADDRESS).get().valueOrDefault(CxField.STATE));
        assertEquals("LILI", accessors.last().valueOrDefault(CxField.NAME));
        assertEquals(now, accessors.head().accessor(CxField.ADDRESS).get().value(CxField.CREATED_AT).get());
    }

    @Test
    public void testPut() {
    }

    @Test
    public void testPut1() {
    }

    @Test
    public void testPut2() {
    }

    @Test
    public void testPut3() {
    }

    @Test
    public void testPut4() {
    }

    @Test
    public void size() {
    }
}