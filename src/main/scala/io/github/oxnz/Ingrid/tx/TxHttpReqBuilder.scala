package io.github.oxnz.Ingrid.tx

import org.apache.http.client.methods.HttpUriRequest

trait TxHttpReqBuilder {
  def buildRequest(record: TxRecord, destSpec: TxDestSpec): HttpUriRequest
}
