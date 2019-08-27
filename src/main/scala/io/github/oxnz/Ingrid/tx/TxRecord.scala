package io.github.oxnz.Ingrid.tx

import org.springframework.core.style.ToStringCreator
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id


@Entity class TxRecord(var cat: TxCategory, var ref: String, var state: String, var city: String) {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)  var id = 0L

  def this() {
    this(null, null, null, null)
  }

  override def toString: String = new ToStringCreator(this).append("id", id).append("cat", cat).append("ref", ref).append("state", state).append("city", city).toString
}