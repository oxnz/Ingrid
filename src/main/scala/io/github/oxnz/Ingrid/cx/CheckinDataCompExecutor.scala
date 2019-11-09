package io.github.oxnz.Ingrid.cx

import io.github.oxnz.Ingrid.tx.TxCategory
import org.springframework.stereotype.Service

@Service
@CxCategory(cat = TxCategory.ADDRESS) class CheckinDataCompExecutor extends CxExecutor {
  override def execute(request: CxRequest) = new CxResponse(true, "data")
}