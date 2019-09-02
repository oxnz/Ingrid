package io.github.oxnz.Ingrid.tx

import javax.persistence.{Entity, _}

import scala.beans.BeanProperty


@Entity class TxResult(_record: TxRecord, @BeanProperty var succ: Boolean, @BeanProperty var msg: String) {
  @Id
  @BeanProperty
  @GeneratedValue(strategy = GenerationType.AUTO) val id = 0L
  @BeanProperty
  @ManyToOne(cascade = Array(CascadeType.DETACH))
  @JoinColumn(name = "record_id") var record: TxRecord = _record

  def this() {
    this(null, false, null)
  }


  override def toString = s"TxResult(id=$id, record=$record, succ=$succ, msg=$msg)"
}
