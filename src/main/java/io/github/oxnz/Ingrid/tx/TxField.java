package io.github.oxnz.Ingrid.tx;

import java.time.LocalDate;

public enum TxField {
    CHECKIN_DATE("Intenal", LocalDate.now(), "transformScript"),
    CHECKOUT_DATE("Vendor", LocalDate.now(), "transformScript");

    public final String dataSource;
    public final Object defaultValue;
    public final String transformScript;

    TxField(String dataSource, Object defaultValue, String transformScript) {
        this.dataSource = dataSource;
        this.defaultValue = defaultValue;
        this.transformScript = transformScript;
    }
}
