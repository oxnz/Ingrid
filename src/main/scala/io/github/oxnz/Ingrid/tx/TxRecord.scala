package io.github.oxnz.Ingrid.tx

import javax.persistence.{Column, Entity, GeneratedValue, GenerationType, Id}

import scala.beans.BeanProperty

@Entity class TxRecord(@BeanProperty var cat: TxCategory, @BeanProperty var ref: String, @BeanProperty var state: String, @BeanProperty var city: String, @BeanProperty var status: TxStatus = TxStatus.CREATED, @BeanProperty var info: String = "new") {
  @Id
  @BeanProperty
  @GeneratedValue(strategy = GenerationType.AUTO) var id = 0L
  @BeanProperty @Column(length=10240) var data: String = _

  def this() {
    this(null, null, null, null)
  }


  override def toString = s"TxRecord(id=$id, data=$data, cat=$cat, ref=$ref, state=$state, city=$city, status=$status, info=$info)"
}