package io.github.oxnz.Ingrid.dts.data;

import io.github.oxnz.Ingrid.dts.TxRecord;
import io.github.oxnz.Ingrid.repo.DataStore;

import java.util.Optional;

public class TxDataRepo implements DataStore<TxRecord, Long> {

    @Override
    public Optional<TxRecord> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public Iterable<TxRecord> findAll() {
        return null;
    }

    @Override
    public Iterable<TxRecord> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public <S extends TxRecord> S save(S entity) {
        return null;
    }

    @Override
    public <S extends TxRecord> Iterable<S> save(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(TxRecord entity) {

    }

    @Override
    public void deleteAll(Iterable<? extends TxRecord> entities) {

    }

    @Override
    public void deleteAll() {

    }

}
