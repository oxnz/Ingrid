package io.github.oxnz.Ingrid.tx

import org.apache.http.client.methods.HttpPost

trait TxHttpReqBuilder {
  def buildRequest(record: TxRecord, destSpec: DestSpec): HttpPost
}
