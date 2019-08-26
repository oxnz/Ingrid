package io.github.oxnz.Ingrid.dts;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TxResultRepo extends CrudRepository<TxResult, Long> {
}
