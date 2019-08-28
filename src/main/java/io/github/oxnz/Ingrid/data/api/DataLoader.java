package io.github.oxnz.Ingrid.data.api;

import javax.persistence.NoResultException;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

public interface DataLoader<T, ID> {
    /**
     * Retrieves an entity by its id.
     *
     * @param id must not be {@literal null}.
     * @return the entity with the given id or {@literal Optional#empty()} if none found
     * @throws IllegalArgumentException if {@code id} is {@literal null}.
     */
    default Optional<T> find(ID id) {
        Iterable<T> records = findAll(Set.of(id));
        Iterator<T> iterator = records.iterator();
        if (iterator.hasNext()) return Optional.of(iterator.next());
        return Optional.empty();
    }

    default T fetch(ID id) throws NoResultException {
        return find(id).orElseThrow(NoResultException::new);
    }

    /**
     * Returns whether an entity with the given id exists.
     *
     * @param id must not be {@literal null}.
     * @return {@literal true} if an entity with the given id exists, {@literal false} otherwise.
     * @throws IllegalArgumentException if {@code id} is {@literal null}.
     */
    default boolean exists(ID id) {
        return find(id).isPresent();
    }

    /**
     * Returns all instances of the type.
     *
     * @return all entities
     */
    default Iterable<T> findAll() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns all instances of the type with the given IDs.
     *
     * @param ids
     * @return
     */
    Iterable<T> findAll(Iterable<ID> ids);

    /**
     * Returns the number of entities available.
     *
     * @return the number of entities
     */
    default long count() {
        throw new UnsupportedOperationException();
    }
}
