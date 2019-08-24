package io.github.oxnz.Ingrid.repo;

public interface DataStore<T, ID> extends DataWriter<T, ID>, DataLoader<T, ID> {
}
