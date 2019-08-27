package io.github.oxnz.Ingrid.tx

import javax.persistence.Entity
import org.springframework.core.style.ToStringCreator
import javax.persistence._

@Entity class TxResult(_record: TxRecord, var succ : Boolean, var msg : String) {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)  val id = 0L
  @ManyToOne(cascade = Array(CascadeType.DETACH))
  @JoinColumn(name = "record_id")  var record : TxRecord = _record

  def this() {
    this(null, false, null)
  }

  override def toString: String = new ToStringCreator(this).append("id", id).append("record", record).append("succeeded", succ).append("message", msg).toString
}
