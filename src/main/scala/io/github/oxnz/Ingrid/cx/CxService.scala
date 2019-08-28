package io.github.oxnz.Ingrid.cx

import io.github.oxnz.Ingrid.tx.TxCategory
import io.micrometer.core.instrument.MeterRegistry
import org.slf4j.{Logger, LoggerFactory}

import scala.collection.mutable

class CxService(private final val metrics: MeterRegistry) {
  final private[cx] val log: Logger = LoggerFactory.getLogger(getClass)
  private final val completors: mutable.Map[TxCategory, CxExecutor] = mutable.Map()

  def register(executor: CxExecutor): Unit = {
    require(executor != null, "executor should not be null")
    val clazz = executor.getClass
    require(clazz.isAnnotationPresent(classOf[CxCategory]), "region annotation is required")
    val cat = clazz.getAnnotation(classOf[CxCategory]).cat()
    require(!completors.contains(cat), "category already registered: " + cat)
    completors.update(cat, executor)
  }

  def process(request: CxRequest): CxResponse = {
    require(request != null, "request should not be null")
    var response = execute(request)
    metrics.counter(MetricsNames.Counter,
      String.valueOf(request.cat),
      String.valueOf(response.succ)).increment()
    response
  }

  private def execute(request: CxRequest): CxResponse = {
    try {
      var executor = dispatch(request)
      executor.execute(request)
    } catch {
      case e: Exception =>
        log.error("execute", e)
        new CxResponse(false, null)
    }
  }

  private def dispatch(request: CxRequest): CxExecutor = {
    completors.apply(request.cat)
  }

  private object MetricsNames {
    val Counter = "cx.service"
  }

}
