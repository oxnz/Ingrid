package io.github.oxnz.Ingrid.data.api;

import java.util.Set;

public interface DataWriter<T, ID> {
    /**
     * Saves a given entity. Use the returned instance for further operations as the save operation
     * might have changed the entity instance completely.
     *
     * @param entity must not be {@literal null}.
     * @return the saved entity will never be {@literal null}.
     */
    default <S extends T> S save(S entity) {
        Iterable<S> records = saveAll(Set.of(entity));
        return records.iterator().next();
    }

    /**
     * Saves all given entities.
     *
     * @param entities must not be {@literal null}.
     * @return the saved entities will never be {@literal null}.
     * @throws IllegalArgumentException in case the given entity is {@literal null}.
     */
    <S extends T> Iterable<S> saveAll(Iterable<S> entities);
    /**
     * Deletes the entity with the given id.
     *
     * @param id must not be {@literal null}.
     * @throws IllegalArgumentException in case the given {@code id} is {@literal null}
     */
    void delete(ID id);

    /**
     * Deletes the given entities.
     *
     * @param ids
     * @throws IllegalArgumentException in case the given {@link Iterable} is {@literal null}.
     */
    void deleteAll(Iterable<ID> ids);

    /** Deletes all entities managed by the repository. */
    default void deleteAll() {
        throw new UnsupportedOperationException();
    }
}
