package io.github.oxnz.Ingrid.avatar;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AvatarService {

    private final AvatarRepository avatarRepository;

    public AvatarService(AvatarRepository avatarRepository) {
        this.avatarRepository = avatarRepository;
    }

    public Avatar save(Avatar avatar) {
        return avatarRepository.save(avatar);
    }

    public void delete(Avatar avatar) {
        avatarRepository.delete(avatar);
    }

    public List<Avatar> findAll() {
        Iterable<Avatar> iterable = avatarRepository.findAll();
        List<Avatar> avatars = new ArrayList<>();
        iterable.iterator().forEachRemaining(avatars::add);
        return avatars;
    }

    Optional<Avatar> find(long id) {
        return avatarRepository.findById(id);
    }
}
