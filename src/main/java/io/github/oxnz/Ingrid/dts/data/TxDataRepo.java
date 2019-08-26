package io.github.oxnz.Ingrid.dts.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TxDataRepo extends CrudRepository<TxRecord, Long> {

    @Override
    List<TxRecord> findAll();
}