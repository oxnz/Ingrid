package io.github.oxnz.Ingrid.cx

import io.github.oxnz.Ingrid.tx.TxCategory
import org.springframework.stereotype.Service

@Service
@CxCategory(cat = TxCategory.ADDRESS) class AddressCompExecutor extends CxExecutor {

  def data(request: CxRequest): String = new CxFieldAccessor()
    .put(CxField.NATION, CxDataSource.EXTERNAL, "Atlantis")
    .put(CxField.STATE, CxDataSource.EXTERNAL, "north-land")
    .put(CxField.CITY, CxDataSource.EXTERNAL, "rivendale")
    .put(CxField.DISTRICT, CxDataSource.EXTERNAL, "downtown")
    .put(CxField.APARTMENT, CxDataSource.EXTERNAL, "THE-13F-#1314")
    .toString

  override def execute(request: CxRequest) = new CxResponse(true, data(request))
}