package io.github.oxnz.Ingrid.tx

import java.io.Serializable

class TxEvent(val id: Long) extends Serializable {
  override def toString = s"TxEvent(id=$id)"
}

object TxEvent {
  def apply(id: Long): TxEvent = new TxEvent(id)
  def unapply(arg: TxEvent): Long = arg.id
}
