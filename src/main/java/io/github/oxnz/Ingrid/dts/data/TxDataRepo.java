package io.github.oxnz.Ingrid.dts.data;

import io.github.oxnz.Ingrid.dts.CxRecord;
import io.github.oxnz.Ingrid.repo.DataStore;

import java.util.Optional;

public class TxDataRepo implements DataStore<CxRecord, Long> {

    @Override
    public Optional<CxRecord> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public Iterable<CxRecord> findAll() {
        return null;
    }

    @Override
    public Iterable<CxRecord> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public <S extends CxRecord> S save(S entity) {
        return null;
    }

    @Override
    public <S extends CxRecord> Iterable<S> save(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(CxRecord entity) {

    }

    @Override
    public void deleteAll(Iterable<? extends CxRecord> entities) {

    }

    @Override
    public void deleteAll() {

    }

}
