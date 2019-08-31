package io.github.oxnz.Ingrid.tx

import org.slf4j.LoggerFactory

import scala.collection.mutable

class TxDispatcher {
  final private val log = LoggerFactory.getLogger(getClass)

  final private val dispatches = mutable.Map.empty[String, mutable.Set[TxDestSpec]]

  private def dispatchKey(state: String, city: String) = {
    require(state != null, "state should no be null")
    require(city != null, "city should not be null")
    String.join("/", state, city)
  }

  private def dispatchKeys(state: String, city: String) = Set(dispatchKey("", ""), dispatchKey(state, ""), dispatchKey(state, city))

  def register(destSpec: TxDestSpec): Unit = {
    require(destSpec != null, "destSpec should not be null")
    val clazz = destSpec.getClass
    require(clazz.isAnnotationPresent(classOf[TxRegion]), "region annotation is required")
    val region = clazz.getAnnotation(classOf[TxRegion])
    val key = dispatchKey(region.state, region.city)
    synchronized(this) {
      dispatches.getOrElseUpdate(key, mutable.Set()).add(destSpec)
    }
  }

  def dispatch(record: TxRecord): Set[TxDestSpec] = {
    require(record != null, "record should not be null")
    log.debug("dispatch: {}", record)
    dispatch(record.state, record.city, record.cat)
  }

  private def dispatch(state: String, city: String, cat: TxCategory): Set[TxDestSpec] = {
    require(cat != null, "category should not be null")
    val keys = dispatchKeys(state, city)
    synchronized(this) {
      keys.map(dispatches.get).filter(_.isDefined).flatten.flatten
    }
  }

  override def toString = s"TxDispatcher(map=$dispatches)"
}