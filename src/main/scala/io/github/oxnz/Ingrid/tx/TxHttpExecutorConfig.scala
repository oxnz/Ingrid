package io.github.oxnz.Ingrid.tx

import scala.concurrent.duration.Duration

class TxHttpExecutorConfig(val timeoutMS: Int, val maxConn: Int, val maxConnPerRoute: Int, val keepAliveMS: Int, val workerCount: Int) {
  require(workerCount <= maxConn, "extra worker would block by insufficient conn")
}
