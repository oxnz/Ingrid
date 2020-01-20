package io.github.oxnz.Ingrid.tx

class TxHttpExecResult(val succ: Boolean, val msg: String) {
  override def toString = s"TxHttpExecResult(succ=$succ, msg=$msg)"
}

object TxHttpExecResult {
  def apply(succ: Boolean, msg: String): TxHttpExecResult = new TxHttpExecResult(succ, msg)
  def unapply(arg: TxHttpExecResult): (Boolean, String) = (arg.succ, arg.msg)
}