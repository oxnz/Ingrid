package io.github.oxnz.Ingrid;

import io.github.oxnz.Ingrid.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/users")
    @ResponseBody
    public ResponseEntity<List<User>> list() {
        List<User> users = userRepository.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("user/{id}")
    @ResponseBody
    public ResponseEntity<User> get(@PathVariable long id) {
        User user = userRepository.findOne(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("user/{id}")
    @ResponseBody
    public ResponseEntity<User> create(@PathVariable long id) {
        User user = new User(id, "name", String.valueOf(Instant.now()));
        userRepository.save(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
}
