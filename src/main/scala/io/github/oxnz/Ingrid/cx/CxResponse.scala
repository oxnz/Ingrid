package io.github.oxnz.Ingrid.cx

class CxResponse(val succ: Boolean, val data: String) {
  override def toString = s"CxResponse(succ=$succ, data=$data)"
}
