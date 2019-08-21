package io.github.oxnz.Ingrid.user

import java.util

import io.github.oxnz.Ingrid.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service

@Service
class UserService(@Autowired private val userRepository: UserRepository) {

  @PreAuthorize("hasRole('admin')")
  def list() : util.List[User] = {
    userRepository.findAll()
  }

  @PreAuthorize("hasRole('user')")
  def user(id: Long) : User = {
    userRepository.findOne(id)
  }

  @PreAuthorize("hasRole('user')")
  def create(user: User) : Long = {
    userRepository.save(user)
    user.id
  }

}
