package io.github.oxnz.Ingrid.dts;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TxResultRepo extends CrudRepository<TxResult, Long> {
    @Override
    public List<TxResult> findAll();
}
