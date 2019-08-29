package io.github.oxnz.Ingrid.avatar;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AvatarRepository extends CrudRepository<Avatar, Long> {
    List<Avatar> findBySize(int size);
}
