package io.github.oxnz.Ingrid.avatar

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
trait AvatarRepository extends CrudRepository[Avatar, Long] {
  def findBySize(size: Int) : List[Avatar]
}
