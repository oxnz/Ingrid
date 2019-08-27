package io.github.oxnz.Ingrid.tx

import org.apache.http.HttpResponse


trait ResponseHandler {
  def handleResponse(response: HttpResponse): TxResult
}
