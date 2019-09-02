package io.github.oxnz.Ingrid.cx

import io.github.oxnz.Ingrid.tx.TxCategory
import io.micrometer.core.instrument.MeterRegistry
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.stereotype.Service

@Service
class CxService(private final val metrics: MeterRegistry, executors: List[CxExecutor]) {
  final private[cx] val log: Logger = LoggerFactory.getLogger(getClass)
  require(executors.nonEmpty, "executors should not be empty")
  private final val dispatches: Map[TxCategory, CxExecutor] =
    executors.groupBy(dispatchKey).map { case (k, v) => (k, v.head) }

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
    dispatches.apply(request.cat)
  }

  private def dispatchKey(executor: CxExecutor): TxCategory = {
    require(executor != null, "executor should not be null")
    val clazz = executor.getClass
    require(clazz.isAnnotationPresent(classOf[CxCategory]), "region annotation is required")
    clazz.getAnnotation(classOf[CxCategory]).cat()
  }

  private object MetricsNames {
    val Counter = "cx.service"
  }

}
