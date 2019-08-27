package io.github.oxnz.Ingrid.cx

import io.github.oxnz.Ingrid.tx.TxCategory

class CxRequest(val id: Long, val category: TxCategory, val dat: String) {
  def getId: Long = id
}