package io.github.oxnz.Ingrid.repo;

import javax.persistence.NoResultException;
import java.util.Optional;

public interface DataLoader<T, ID> {
    Optional<T> findById(ID id);

    default T fetchById(ID id) {
        return findById(id).orElseThrow(NoResultException::new);
    }

    default boolean existsById(ID id) {
        return findById(id).isPresent();
    }

    Iterable<T> findAll();

    Iterable<T> findAllById(Iterable<ID> ids);

    long count();
}
