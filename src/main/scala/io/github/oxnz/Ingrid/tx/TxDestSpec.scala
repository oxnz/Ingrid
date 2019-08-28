package io.github.oxnz.Ingrid.tx

import org.apache.http.client.ResponseHandler

trait TxDestSpec {
  def isInterested(cat: TxCategory): Boolean

  def responseHandler: ResponseHandler[_ <: TxHttpExecResult]

  def requestBuilder: TxHttpReqBuilder
}