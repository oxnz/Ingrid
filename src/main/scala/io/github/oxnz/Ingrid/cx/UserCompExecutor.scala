package io.github.oxnz.Ingrid.cx

import io.github.oxnz.Ingrid.tx.TxCategory
import org.springframework.stereotype.Service

@Service
@CxCategory(cat = TxCategory.USER) class UserCompExecutor extends CxExecutor {
  override def execute(request: CxRequest) = new CxResponse(true, "value")
}