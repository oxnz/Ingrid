package io.github.oxnz.Ingrid.dts.data;

import io.github.oxnz.Ingrid.dts.TxCategory;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.Entity;

@Entity
public class TxRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public TxCategory getCat() {
        return cat;
    }

    public String getRef() {
        return ref;
    }

    private TxCategory cat;
    private String ref;

    public TxRecord(TxCategory cat, String ref) {
        this.cat = cat;
        this.ref = ref;
    }

    public TxRecord() {
    }

    public Long getId() {
        return id;
    }
}
