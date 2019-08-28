package io.github.oxnz.Ingrid.user

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service

@Service
class UserService(@Autowired private val userRepository: UserRepository) {

  @PreAuthorize("hasRole('admin')")
  def list() = {
    userRepository.findAll
  }

  @PreAuthorize("hasRole('user')")
  def user(id: Long) = {
    userRepository.findOne(id)
  }

  @PreAuthorize("hasRole('user')")
  def create(user: User) : Long = {
    userRepository.save(user)
    user.id
  }

}
