package io.github.oxnz.Ingrid.tx

import scala.concurrent.duration.Duration

class TxHttpExecutorConfig(val timeout: Duration, val maxConn: Int, val maxConnPerRoute: Int, val KeepAlive: Duration, val workerCount: Int) {
  require(workerCount <= maxConn, "extra worker would block by insufficient conn")
}
