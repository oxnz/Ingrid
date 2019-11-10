package io.github.oxnz.Ingrid.tx

import org.springframework.stereotype.Component

@Component
class TxDispatcher(destSpecs: List[TxDestSpec]) {
  require(destSpecs.nonEmpty, "destSepcs should not be empty")

  private val dispatches: Map[String, Set[TxDestSpec]] = destSpecs.groupBy(dispatchKey)
    .map { case (k, v) => (k, v.toSet) }

  def dispatch(record: TxRecord): Set[TxDestSpec] = {
    require(record != null, "record should not be null")
    dispatch(record.state, record.city, record.cat)
  }

  private def dispatch(state: String, city: String, cat: TxCategory): Set[TxDestSpec] = {
    require(cat != null, "category should not be null")
    dispatchKeys(state, city).collect(dispatches).flatten.filter(_.isInterested(cat))
  }

  private def dispatchKeys(state: String, city: String) = Set(dispatchKey("", ""), dispatchKey(state, ""), dispatchKey(state, city))

  private def dispatchKey(state: String, city: String) = {
    require(state != null, "state should no be null")
    require(city != null, "city should not be null")
    String.join("/", state, city)
  }

  override def toString = s"TxDispatcher(map=$dispatches)"

  private def dispatchKey(destSpec: TxDestSpec): String = {
    require(destSpec != null, "destSpec should not be null")
    val clazz = destSpec.getClass
    require(clazz.isAnnotationPresent(classOf[TxRegion]), "region annotation is required")
    val region = clazz.getAnnotation(classOf[TxRegion])
    dispatchKey(region.state, region.city)
  }
}