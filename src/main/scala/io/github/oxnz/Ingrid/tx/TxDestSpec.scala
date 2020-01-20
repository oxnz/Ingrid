package io.github.oxnz.Ingrid.tx

import org.apache.http.protocol.HttpContext

trait TxDestSpec {
  def isInterested(cat: TxCategory): Boolean

  def responseHandler: TxHttpRespHandler

  def requestBuilder: TxHttpReqBuilder

  def httpContext: HttpContext
}