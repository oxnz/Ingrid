package io.github.oxnz.Ingrid.dts.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TxDataRepo extends CrudRepository<TxRecord, Long> {
}