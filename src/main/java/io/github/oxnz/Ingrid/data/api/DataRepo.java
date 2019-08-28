package io.github.oxnz.Ingrid.data.api;

public interface DataRepo<T, ID> extends DataLoader<T, ID>, DataWriter<T, ID> {}