package io.github.oxnz.Ingrid.tx

import org.springframework.core.style.ToStringCreator
import java.io.Serializable

class TxEvent(val id: Long) extends Serializable {


  override def toString = s"TxEvent(id=$id)"
}
