package io.github.oxnz.Ingrid.tx

import java.io.IOException
import java.util.concurrent.ExecutionException

import io.github.oxnz.Ingrid.cx.{CxRequest, CxResponse, CxService}
import javax.persistence.NoResultException
import org.apache.http.client.methods.HttpPost
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.stereotype.Service

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
      destSpecs.map(post(record, _))
      record.status = TxStatus.SENT
    } catch {
      case e: Exception =>
        record.status = TxStatus.FAILED
        throw new TxException(e)
    } finally txRecordRepo.save(record)
  }

  private def post(record: TxRecord, destSpec: TxDestSpec): Unit = {
    val httpExecResult: TxHttpExecResult = doPost(record, destSpec)
    val txResult: TxResult = new TxResult(record, httpExecResult.succeeded, httpExecResult.message)
    txResultRepo.save(txResult)
    log.debug("result: {}", txResult)
  }

  private def doPost(record: TxRecord, destSpec: TxDestSpec): TxHttpExecResult = {
    val request: HttpPost = destSpec.requestBuilder.buildRequest(record, destSpec)
    try
      txHttpExecutor.execute(request, null, destSpec.responseHandler)
    catch {
      case e@(_: InterruptedException | _: ExecutionException) =>
        log.error("post", e)
        new TxHttpExecResult(false, e.getMessage)
    }
  }

  @throws[IOException]
  override def close(): Unit = {
    txHttpExecutor.close()
  }
}
