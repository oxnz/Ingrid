package io.github.oxnz.Ingrid.cx

trait CxExecutor {

  @throws[CxException]
  def execute(request: CxRequest): CxResponse

}
