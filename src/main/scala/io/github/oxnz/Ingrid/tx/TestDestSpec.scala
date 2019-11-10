package io.github.oxnz.Ingrid.tx

import java.io.UnsupportedEncodingException
import java.net.URI

import org.apache.http.client.config.RequestConfig
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.StringEntity
import org.apache.http.protocol.HttpContext
import org.apache.http.{HttpEntity, HttpHeaders}
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
@TxRegion(state = "never-land", city = "lost-city") class TestDestSpec extends TxDestSpec {
  final private val log = LoggerFactory.getLogger(getClass)
  final private val CHECKIN_URI = URI.create("http://localhost:8000/echo")
  final private val requestConfig = RequestConfig.DEFAULT

  override def isInterested(cat: TxCategory): Boolean = TxCategory.ADDRESS == cat

  override val responseHandler: TxHttpRespHandler = {
    entity: HttpEntity => {
      log.debug("entity: {}", entity)
      new TxHttpExecResult(true, "success")
    }
  }

  override val requestBuilder: TxHttpReqBuilder = (record: TxRecord, txDestSpec: TxDestSpec) => {
    val request = new HttpPost(CHECKIN_URI)
    try {
      val entity = new StringEntity(record.toString)
      request.addHeader(HttpHeaders.CONTENT_TYPE, "text/plain")
      request.setEntity(entity)
      request.setConfig(requestConfig)
      log.debug("req: {}, entity: {}", request, entity)
      request
    } catch {
      case e: UnsupportedEncodingException =>
        throw new RuntimeException(e)
    }
  }

  override val httpContext: HttpContext = null
}