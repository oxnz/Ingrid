package io.github.oxnz.Ingrid.cx

trait CxExecutor {

  def execute(request: CxRequest): CxResponse

}
