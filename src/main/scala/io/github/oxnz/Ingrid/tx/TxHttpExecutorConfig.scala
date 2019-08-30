package io.github.oxnz.Ingrid.tx

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

import scala.beans.BeanProperty

@Component
@ConfigurationProperties(prefix = "tx.http.executor")
class TxHttpExecutorConfig(@BeanProperty var timeoutMillis: Int = 10 * 1000, @BeanProperty var maxConn: Int = 8 * 8, @BeanProperty var maxConnPerRoute: Int = 8, @BeanProperty var keepAliveTimeoutMillis: Int = 30 * 1000, @BeanProperty var workerCount: Int = 8) {
  require(workerCount <= maxConn, "extra worker would block by insufficient conn")

  override def toString = s"TxHttpExecutorConfig(timeoutMS=$timeoutMillis, maxConn=$maxConn, maxConnPerRoute=$maxConnPerRoute, keepAliveMS=$keepAliveTimeoutMillis, workerCount=$workerCount)"
}
