package io.github.oxnz.Ingrid.tx

import org.slf4j.LoggerFactory

class TxDispatcher(destSpecs: List[TxDestSpec]) {
  require(destSpecs.nonEmpty, "destSepcs should not be empty")

  private val dispatches: Map[String, Set[TxDestSpec]] = destSpecs.groupBy(dispatchKey)
    .map { case (k, v) => (k, v.toSet) }

  final private val log = LoggerFactory.getLogger(getClass)

  def this(destSpecs: TxDestSpec*) = {
    this(destSpecs.toList)
  }

  private def dispatchKey(destSpec: TxDestSpec): String = {
    require(destSpec != null, "destSpec should not be null")
    val clazz = destSpec.getClass
    require(clazz.isAnnotationPresent(classOf[TxRegion]), "region annotation is required")
    val region = clazz.getAnnotation(classOf[TxRegion])
    dispatchKey(region.state, region.city)
  }

  private def dispatchKey(state: String, city: String) = {
    require(state != null, "state should no be null")
    require(city != null, "city should not be null")
    String.join("/", state, city)
  }

  private def dispatchKeys(state: String, city: String) = Set(dispatchKey("", ""), dispatchKey(state, ""), dispatchKey(state, city))

  def dispatch(record: TxRecord): Set[TxDestSpec] = {
    require(record != null, "record should not be null")
    log.debug("dispatch: {}", record)
    dispatch(record.state, record.city, record.cat)
  }

  private def dispatch(state: String, city: String, cat: TxCategory): Set[TxDestSpec] = {
    require(cat != null, "category should not be null")
    val keys = dispatchKeys(state, city)
    keys.map(dispatches.get).filter(_.isDefined).flatten.flatten.filter(_.intestedCats.contains(cat))
  }

  override def toString = s"TxDispatcher(map=$dispatches)"
}