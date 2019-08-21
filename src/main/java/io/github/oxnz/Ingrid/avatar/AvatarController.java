package io.github.oxnz.Ingrid.avatar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("avatar")
public class AvatarController {

    @Autowired
    private AvatarService avatarService;

    @RequestMapping("/findAll")
    @ResponseBody
    public ResponseEntity<List<Avatar>> list() {
        List<Avatar> avatars = avatarService.findAll();
        return new ResponseEntity<>(avatars, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Avatar> get(@PathVariable long id) {
        Avatar avatar = avatarService.find(id).orElse(null);
        return new ResponseEntity<>(avatar, HttpStatus.OK);
    }

    @PostMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Avatar> create(@PathVariable long id) {
        Avatar avatar = new Avatar(128, String.valueOf(Instant.now()));
        avatar = avatarService.save(avatar);
        return new ResponseEntity<>(avatar, HttpStatus.CREATED);
    }
}
