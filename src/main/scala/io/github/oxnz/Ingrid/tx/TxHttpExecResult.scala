package io.github.oxnz.Ingrid.tx

class TxHttpExecResult(val succ: Boolean, val msg: String) {
  def succeeded: Boolean = succ

  def message: String = msg
}