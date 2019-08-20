package io.github.oxnz.Ingrid;

import io.github.oxnz.Ingrid.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/list")
    @ResponseBody
    public ResponseEntity<List<User>> list() {
        List<User> users = userRepository.list();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<User> get(@PathVariable long id) {
        User user = userRepository.get(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/{id}")
    @ResponseBody
    public ResponseEntity<User> create(@PathVariable long id) {
        User user = new User(id, "name", String.valueOf(Instant.now()));
        userRepository.put(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
}
