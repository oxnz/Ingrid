package io.github.oxnz.Ingrid.tx

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
trait TxRecordRepo extends CrudRepository[TxRecord, Long] {
}
