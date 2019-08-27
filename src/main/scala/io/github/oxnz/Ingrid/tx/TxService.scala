package io.github.oxnz.Ingrid.tx

import java.io.IOException
import java.util
import java.util.Set
import java.util.concurrent.ExecutionException

import javax.persistence.NoResultException
import org.apache.http.client.methods.HttpPost
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.stereotype.Service

@Service class TxService(val txRecordRepo: TxRecordRepo, val txResultRepo: TxResultRepo, val dispatcher: Dispatcher, val httpExecutionService: HttpExecutionService) extends AutoCloseable {
  final private[tx] val log: Logger = LoggerFactory.getLogger(getClass)

  @throws[TxException]
  def process(id: Long): Unit = {
    try {
      val record: TxRecord = txRecordRepo.findById(id).orElseThrow(() => new NoResultException("record not found: " + id))
      log.debug("record: {}", record)
      // TODO: cxService
      // cxRecord cxRecord = cxService.complete(record.getId());
      // record.data = cxRecord.toString();
      // txRecordRepo.save(record);
      val destSpecs: util.Set[DestSpec] = dispatcher.dispatch(record)
      destSpecs.forEach((destSpec: DestSpec) => post(record, destSpec))
    } catch {
      case e: Exception =>
        throw new TxException(e)
    }
  }

  private def post(record: TxRecord, destSpec: DestSpec): Unit = {
    val httpExecResult: HttpExecutionResult = doPost(record, destSpec)
    val txResult: TxResult = new TxResult(record, httpExecResult.succeeded, httpExecResult.message)
    txResultRepo.save(txResult)
    log.debug("result: {}", txResult)
  }

  private def doPost(record: TxRecord, destSpec: DestSpec): HttpExecutionResult = {
    val request: HttpPost = destSpec.requestBuilder.buildRequest(record, destSpec)
    try
      httpExecutionService.execute(request, null, destSpec.responseHandler)
    catch {
      case e@(_: InterruptedException | _: ExecutionException) =>
        log.error("post", e)
        new HttpExecutionResult(false, e.getMessage)
    }
  }

  @throws[IOException]
  override def close(): Unit = {
    httpExecutionService.close()
  }
}
