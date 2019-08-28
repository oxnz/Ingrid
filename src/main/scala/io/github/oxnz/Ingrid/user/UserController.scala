package io.github.oxnz.Ingrid.user

import java.time.Instant

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.{HttpStatus, ResponseEntity}
import org.springframework.web.bind.annotation._

@RestController class UserController {
  @Autowired private val userRepository: UserRepository = null

  @RequestMapping(Array("/users"))
  @ResponseBody def list: ResponseEntity[List[User]] = {
    val users = userRepository.findAll
    new ResponseEntity[List[User]](users, HttpStatus.OK)
  }

  @GetMapping(Array("user/{id}"))
  @ResponseBody def get(@PathVariable id: Long): ResponseEntity[User] = {
    val user = userRepository.findOne(id)
    new ResponseEntity[User](user, HttpStatus.OK)
  }

  @PostMapping(Array("user/{id}"))
  @ResponseBody def create(@PathVariable id: Long): ResponseEntity[User] = {
    val user = new User(id, "name", String.valueOf(Instant.now))
    userRepository.save(user)
    new ResponseEntity[User](user, HttpStatus.CREATED)
  }
}