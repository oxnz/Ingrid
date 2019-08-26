package io.github.oxnz.Ingrid.dts;

import io.github.oxnz.Ingrid.dts.data.TxRecord;

import javax.persistence.*;

@Entity
public class TxResult {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "record_id")
    private TxRecord record;
    public  boolean succ;
    public  String msg;

    public TxResult() {}

    public TxResult(TxRecord record, boolean succ, String msg) {
        this.record = record;
        this.succ = succ;
        this.msg = msg;
    }
}
