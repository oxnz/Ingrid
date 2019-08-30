package io.github.oxnz.Ingrid.tx

import java.io.IOException

import org.apache.http.client.{HttpResponseException, ResponseHandler}
import org.apache.http.util.EntityUtils
import org.apache.http.{HttpEntity, HttpResponse}

trait TxHttpRespHandler extends ResponseHandler[TxHttpExecResult] {

  /**
    * Read the entity from the response body and pass it to the entity handler
    * method if the response was successful (a 2xx status code). If no response
    * body exists, this returns failed result. If the response was unsuccessful (&gt;= 300
    * status code), throws an {@link HttpResponseException}.
    */
  @throws[HttpResponseException]
  @throws[IOException]
  override def handleResponse(response: HttpResponse): TxHttpExecResult = {
    val statusLine = response.getStatusLine
    val entity = response.getEntity
    if (statusLine.getStatusCode >= 300) {
      EntityUtils.consume(entity)
      throw new HttpResponseException(statusLine.getStatusCode, statusLine.getReasonPhrase)
    }
    if (entity == null) new TxHttpExecResult(false, "null entity")
    else handleEntity(entity)
  }

  /**
    * Handle the response entity and transform it into the actual response
    * object.
    */
  @throws[IOException]
  def handleEntity(entity: HttpEntity): TxHttpExecResult
}
