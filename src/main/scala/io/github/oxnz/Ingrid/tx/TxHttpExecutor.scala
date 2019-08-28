package io.github.oxnz.Ingrid.tx

import java.io.IOException
import java.security.{KeyManagementException, KeyStoreException, NoSuchAlgorithmException}
import java.util.concurrent.{ExecutionException, Executors}
import java.util.concurrent.atomic.AtomicBoolean

import javax.annotation.PreDestroy
import javax.net.ssl.HostnameVerifier
import org.apache.http.HttpResponse
import org.apache.http.client.ResponseHandler
import org.apache.http.client.config.RequestConfig
import org.apache.http.client.methods.HttpUriRequest
import org.apache.http.conn.ssl.{DefaultHostnameVerifier, SSLConnectionSocketFactory, TrustSelfSignedStrategy, TrustStrategy}
import org.apache.http.impl.client.{FutureRequestExecutionService, HttpClients, HttpRequestFutureTask}
import org.apache.http.protocol.HttpContext
import org.apache.http.ssl.SSLContextBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service class TxHttpExecutor(val config: TxHttpExecutorConfig, val trustStrategy: TrustStrategy, val hostnameVerifier: HostnameVerifier) extends AutoCloseable {
  final private val closed = new AtomicBoolean(false)
  final private var executionService: FutureRequestExecutionService = _
  try {
    val sslcontext = SSLContextBuilder.create.loadTrustMaterial(trustStrategy).build
    val sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslcontext, hostnameVerifier)
    val requestConfig = RequestConfig.custom
      .setConnectTimeout(config.timeoutMillis)
      .setConnectionRequestTimeout(config.timeoutMillis)
      .setSocketTimeout(config.timeoutMillis)
      .build
    val httpClient = HttpClients.custom
      .setDefaultRequestConfig(requestConfig)
      .setMaxConnTotal(config.maxConn)
      .setMaxConnPerRoute(config.maxConnPerRoute)
      .setKeepAliveStrategy((response: HttpResponse, context: HttpContext) => config.keepAliveTimeoutMillis)
      .setSSLSocketFactory(sslConnectionSocketFactory)
      .build
    val executor = Executors.newFixedThreadPool(config.workerCount)
    executionService = new FutureRequestExecutionService(httpClient, executor)
  } catch {
    case e@(_: NoSuchAlgorithmException | _: KeyStoreException | _: KeyManagementException) =>
      throw new RuntimeException(e)
  }

  @Autowired
  def this(config: TxHttpExecutorConfig) {
    this(config, new TrustSelfSignedStrategy, new DefaultHostnameVerifier)
  }

  @throws[ExecutionException]
  @throws[InterruptedException]
  def execute[T](request: HttpUriRequest, context: HttpContext, responseHandler: ResponseHandler[T]): T = {
    val futureTask = submit(request, context, responseHandler)
    val response = futureTask.get
    response
  }

  def submit[T](request: HttpUriRequest, context: HttpContext, responseHandler: ResponseHandler[T]): HttpRequestFutureTask[T] = executionService.execute(request, context, responseHandler)

  @throws[IOException]
  override def close(): Unit = {
    if (!closed.get) executionService.close()
  }
}

