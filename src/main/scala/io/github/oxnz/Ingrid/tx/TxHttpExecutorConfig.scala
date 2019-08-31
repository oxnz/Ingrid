package io.github.oxnz.Ingrid.tx

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

import scala.beans.BeanProperty

@Component
@ConfigurationProperties(prefix = "tx.http.executor")
class TxHttpExecutorConfig(@BeanProperty var timeoutMillis: Int, @BeanProperty var maxConn: Int, @BeanProperty var maxConnPerRoute: Int, @BeanProperty var keepAliveTimeoutMillis: Int, @BeanProperty var workerCount: Int) {
  require(workerCount <= maxConn, "extra worker would block by insufficient conn")

  def this() {
    this(timeoutMillis = 10 * 1000, maxConn = 8 * 8, maxConnPerRoute = 8, keepAliveTimeoutMillis = 30 * 1000, workerCount = 8)
  }

  override def toString = s"TxHttpExecutorConfig(timeoutMS=$timeoutMillis, maxConn=$maxConn, maxConnPerRoute=$maxConnPerRoute, keepAliveMS=$keepAliveTimeoutMillis, workerCount=$workerCount)"
}
