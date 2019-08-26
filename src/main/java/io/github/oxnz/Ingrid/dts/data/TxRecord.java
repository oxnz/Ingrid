package io.github.oxnz.Ingrid.dts.data;

import io.github.oxnz.Ingrid.dts.TxCategory;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class TxRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private TxCategory cat;
    private String ref;

    public TxRecord(TxCategory cat, String ref) {
        this.cat = cat;
        this.ref = ref;
    }
    public TxRecord() {
    }

    public TxCategory getCat() {
        return cat;
    }

    public String getRef() {
        return ref;
    }

    public Long getId() {
        return id;
    }
}
