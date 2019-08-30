package io.github.oxnz.Ingrid.tx

trait TxDestSpec {
  def isInterested(cat: TxCategory): Boolean

  def responseHandler: TxHttpRespHandler

  def requestBuilder: TxHttpReqBuilder
}