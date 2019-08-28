package io.github.oxnz.Ingrid.tx

import java.io.UnsupportedEncodingException
import java.net.URI

import org.apache.http.client.methods.HttpPost
import org.apache.http.client.{ClientProtocolException, ResponseHandler}
import org.apache.http.entity.StringEntity
import org.apache.http.{HttpHeaders, HttpResponse, HttpStatus, StatusLine}
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
@TxRegion(state = "CA", city = "SF") class TestDestSpec extends TxDestSpec {
  final private val log = LoggerFactory.getLogger(getClass)
  final private val CHECKIN_URI = URI.create("http://localhost:8000/echo")

  override def isInterested(cat: TxCategory) = true

  override def responseHandler: ResponseHandler[_ <: TxHttpExecResult] = {
    response: HttpResponse => {
      log.debug("resp: {}", response)
      val statusLine: StatusLine = response.getStatusLine
      val statusCode = statusLine.getStatusCode
      if (statusCode != HttpStatus.SC_OK)
        throw new ClientProtocolException("unexpected status code: " + statusCode)
      val entity = response.getEntity
      if (entity == null) throw new RuntimeException("no entity")
      new TxHttpExecResult(true, "success")
    }
  }

  override def requestBuilder: TxHttpReqBuilder = (record: TxRecord, txDestSpec: TxDestSpec) => {
    def foo(record: TxRecord, txDestSpec: TxDestSpec) = {
      val request = new HttpPost(CHECKIN_URI)
      try {
        val entity = new StringEntity(record.toString)
        request.addHeader(HttpHeaders.CONTENT_TYPE, "text/plain")
        request.setEntity(entity)
        log.debug("req: {}, entity: {}", request, entity)
        request
      } catch {
        case e: UnsupportedEncodingException =>
          throw new RuntimeException(e)
      }
    }

    foo(record, txDestSpec)
  }
}