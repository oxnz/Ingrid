package io.github.oxnz.Ingrid.tx

import org.apache.http.concurrent.FutureCallback

class TxHttpExecCallback extends FutureCallback[TxResult] {
  override final def cancelled(): Unit = {
    // no-op
  }

  override def completed(result: TxResult): Unit = {
  }

  override def failed(ex: Exception): Unit = {
  }
}