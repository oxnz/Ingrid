package io.github.oxnz.Ingrid.tx

import java.io.IOException

import io.github.oxnz.Ingrid.cx.{CxRequest, CxResponse, CxService}
import javax.persistence.NoResultException
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.stereotype.Service

import scala.util.{Failure, Success, Try}

@Service class TxService(val txRecordRepo: TxRecordRepo, val txResultRepo: TxResultRepo, val cxService: CxService, val dispatcher: TxDispatcher, val txHttpExecutor: TxHttpExecutor) extends AutoCloseable {
  final private[tx] val log: Logger = LoggerFactory.getLogger(getClass)

  @throws[TxException]
  def process(id: Long): Unit = {
    val record: TxRecord = txRecordRepo.findById(id).orElseThrow(() => new NoResultException("record not found: " + id))
    log.debug("record: {}", record)
    try {
      record.status = TxStatus.CONSUMED
      val cxRequest: CxRequest = new CxRequest(record.cat, record.ref)
      val cxResponse: CxResponse = cxService.process(cxRequest)
      if (!cxResponse.succ) throw new IllegalStateException("cxService failed")
      record.status = TxStatus.COMPLETED
      record.data = cxResponse.data
      txRecordRepo.save(record)
      val destSpecs: Set[TxDestSpec] = dispatcher.dispatch(record)
      log.debug("dispatch: {} => {}", record, destSpecs)
      destSpecs.foreach(process(record, _))
      record.status = TxStatus.SENT
    } catch {
      case e: Exception =>
        record.status = TxStatus.FAILED
        throw new TxException(e)
    } finally txRecordRepo.save(record)
  }

  private def process(record: TxRecord, destSpec: TxDestSpec): Unit = {
    val httpExecResult: TxHttpExecResult = transfer(record, destSpec) match {
      case Success(result) => result
      case Failure(exception) =>
        log.error("transfer", exception)
        new TxHttpExecResult(false, exception.getMessage)
    }
    val txResult: TxResult = new TxResult(record, httpExecResult.succ, httpExecResult.msg)
    txResultRepo.save(txResult)
    log.debug("result: {}", txResult)
  }

  private def transfer(record: TxRecord, destSpec: TxDestSpec): Try[TxHttpExecResult] = Try {
    val request = destSpec.requestBuilder.buildRequest(record, destSpec)
    txHttpExecutor.execute(request, destSpec.httpContext, destSpec.responseHandler)
  }

  @throws[IOException]
  override def close(): Unit = {
    txHttpExecutor.close()
  }
}
