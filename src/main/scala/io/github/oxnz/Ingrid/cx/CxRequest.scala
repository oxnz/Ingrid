package io.github.oxnz.Ingrid.cx

import io.github.oxnz.Ingrid.tx.TxCategory

class CxRequest(val cat: TxCategory, val reference: String) {
  override def toString = s"CxRequest(cat=$cat, reference=$reference)"
}