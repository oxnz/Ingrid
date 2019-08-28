package io.github.oxnz.Ingrid.tx

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

import scala.beans.BeanProperty

@Component
@ConfigurationProperties(prefix = "tx.http.executor")
class TxHttpExecutorConfig(@BeanProperty var timeoutMS: Int, @BeanProperty var maxConn: Int, @BeanProperty var maxConnPerRoute: Int, @BeanProperty var keepAliveMS: Int, @BeanProperty var workerCount: Int) {
  require(workerCount <= maxConn, "extra worker would block by insufficient conn")

  def this() = {
    this(1 * 1000, 8 * 8, 8, 30 * 1000, 8)
  }

  override def toString = s"TxHttpExecutorConfig(timeoutMS=$timeoutMS, maxConn=$maxConn, maxConnPerRoute=$maxConnPerRoute, keepAliveMS=$keepAliveMS, workerCount=$workerCount)"
}
