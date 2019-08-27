package io.github.oxnz.Ingrid.tx

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util


@Repository trait TxResultRepo extends CrudRepository[TxResult, Long] {
  override def findAll: util.List[TxResult]
}
