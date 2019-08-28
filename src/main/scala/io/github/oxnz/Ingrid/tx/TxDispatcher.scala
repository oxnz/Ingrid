package io.github.oxnz.Ingrid.tx

import java.util.Objects

import org.slf4j.LoggerFactory
import org.springframework.util.Assert

import scala.collection.mutable

class TxDispatcher {
  final private val log = LoggerFactory.getLogger(getClass)
  final private val dispatches: mutable.Map[String, mutable.Set[DestSpec]] = mutable.Map()

  private def dispatchKey(state: String, city: String) = {
    require(state != null, "state should no be null")
    require(city != null, "city should not be null")
    String.join("/", state, city)
  }

  private def dispatchKeys(state: String, city: String) = Set(dispatchKey("", ""), dispatchKey(state, ""), dispatchKey(state, city))

  def register(destSpec: DestSpec): Unit = {
    require(destSpec != null, "destSpec should not be null")
    val clazz = destSpec.getClass
    require(clazz.isAnnotationPresent(classOf[Region]), "region annotation is required")
    val region = clazz.getAnnotation(classOf[Region])
    val key = dispatchKey(region.state, region.city)
    dispatches.update(key, (dispatches.getOrElse(key, mutable.Set()) + destSpec))
  }

  def dispatch(record: TxRecord): Set[DestSpec] = {
    require(record != null, "record should not be null")
    log.debug("dispatch: {}", record)
    dispatch(record.state, record.city, record.cat)
  }

  private def dispatch(state: String, city: String, cat: TxCategory): Set[DestSpec] = {
    require(cat != null, "category should not be null")
    val keys = dispatchKeys(state, city)
    keys.map(dispatches.get(_)).filter(!_.isEmpty).flatten.flatten
  }
}