package io.github.oxnz.Ingrid.dts;

import io.github.oxnz.Ingrid.dts.data.TxRecord;

import javax.persistence.*;

@Entity
public class TxResult {
    public boolean succ;
    public String msg;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "record_id")
    private TxRecord record;

    public TxResult() {
    }

    public TxResult(TxRecord record, boolean succ, String msg) {
        this.record = record;
        this.succ = succ;
        this.msg = msg;
    }
}
