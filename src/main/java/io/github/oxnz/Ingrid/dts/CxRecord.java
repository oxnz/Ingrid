package io.github.oxnz.Ingrid.dts;

public class CxRecord {
    final long id;
    final TxCategory category;
    final String dat;

    public CxRecord(long id, TxCategory category, String dat) {
        this.id = id;
        this.category = category;
        this.dat = dat;
    }

    public long getId() {
        return id;
    }
}
