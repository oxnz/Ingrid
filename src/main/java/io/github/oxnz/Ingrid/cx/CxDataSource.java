package io.github.oxnz.Ingrid.cx;

public enum CxDataSource {
    INTERNAL("internal"),
    EXTERNAL("external");

    private final String source;

    CxDataSource(String source) {
        this.source = source;
    }
}
