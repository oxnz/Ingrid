package io.github.oxnz.Ingrid.dts.data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class TxRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
}
