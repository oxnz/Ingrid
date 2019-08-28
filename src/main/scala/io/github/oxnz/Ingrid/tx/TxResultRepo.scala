package io.github.oxnz.Ingrid.tx

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository trait TxResultRepo extends CrudRepository[TxResult, Long] {
}
