package io.github.oxnz.Ingrid.cx;

import org.junit.Test;
import scala.Option;

import static org.junit.Assert.*;

public class CxFieldAccessorTest {

    @Test
    public void opt() {
        CxFieldAccessor accessor = new CxFieldAccessor();
        accessor.put(CxField.ID, CxDataSource.INTERNAL, 1234L, -1L);
        assertEquals(1234L, accessor.opt(CxField.ID).get().value());
        assertTrue(accessor.opt(CxField.NAME).isEmpty());
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
        assertEquals("CHEN", accessor.valueOrDefault(CxField.NAME).get());
        assertEquals("LILI", accessor.valueOrDefault(CxField.NAME, "LILI"));
        accessor.put(CxField.ID, CxDataSource.INTERNAL, 1234L, -1L);
        assertEquals(1234L, accessor.valueOrDefault(CxField.ID).get());
        assertEquals(Long.valueOf(1234L), accessor.valueOrDefault(CxField.ID, -9999L));
    }

    @Test
    public void put() {
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