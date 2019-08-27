package io.github.oxnz.Ingrid.tx

import org.apache.http.client.methods.HttpPost

trait RequestBuilder {
  def buildRequest(record: TxRecord, destSpec: DestSpec): HttpPost

}
