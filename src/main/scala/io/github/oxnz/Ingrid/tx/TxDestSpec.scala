package io.github.oxnz.Ingrid.tx

import org.apache.http.protocol.HttpContext

trait TxDestSpec {
  def intestedCats: Set[TxCategory]

  def responseHandler: TxHttpRespHandler

  def requestBuilder: TxHttpReqBuilder

  def httpContext: HttpContext
}