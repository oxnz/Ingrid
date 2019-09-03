package io.github.oxnz.Ingrid.cx

import io.github.oxnz.Ingrid.tx.TxCategory
import io.micrometer.core.instrument.MeterRegistry
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.stereotype.Service

import scala.util.{Failure, Success, Try}

@Service
class CxService(private final val metrics: MeterRegistry, executors: List[CxExecutor]) {
  final private[cx] val log: Logger = LoggerFactory.getLogger(getClass)
  require(executors.nonEmpty, "executors should not be empty")
  private final val dispatches: Map[TxCategory, CxExecutor] =
    executors.groupBy(dispatchKey).map { case (k, v) => (k, v.head) }

  @throws[CxException]
  def process(request: CxRequest): CxResponse = {
    require(request != null, "request should not be null")
    try {
      execute(request)
    } catch {
      case e: Exception => throw new CxException(e)
    }
  }

  private def execute(request: CxRequest): CxResponse = {
    var executor = dispatch(request)
    var response: CxResponse = Try(executor.execute(request)) match {
      case Success(resp) => resp
      case Failure(exception) => new CxResponse(false, exception.getMessage)
    }
    metrics.counter(MetricsNames.Counter,
      String.valueOf(request.cat),
      String.valueOf(response.succ)).increment()
    response
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
