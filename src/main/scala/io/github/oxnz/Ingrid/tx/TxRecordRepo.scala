package io.github.oxnz.Ingrid.tx

import java.{lang, util}

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
trait TxRecordRepo extends CrudRepository[TxRecord, Long] {
  override def findAll: util.List[TxRecord]
}
