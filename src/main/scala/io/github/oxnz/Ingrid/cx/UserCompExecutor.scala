package io.github.oxnz.Ingrid.cx

import java.time.{Clock, LocalDateTime, ZoneId}

import io.github.oxnz.Ingrid.tx.TxCategory
import org.springframework.stereotype.Service

@Service
@CxCategory(cat = TxCategory.USER) class UserCompExecutor(final private val addressCompExecutor: AddressCompExecutor) extends CxExecutor {
  override def execute(request: CxRequest) = new CxResponse(true, data(request))

  private def data(request: CxRequest) = {
    val ts = timestamp(Clock.systemUTC())
    new CxFieldAccessor()
      .put(CxField.ID, CxDataSource.INTERNAL, 9527L)
      .put(CxField.NAME, CxDataSource.INTERNAL, "Chen")
      .put(CxField.ADDRESS, address(request))
      .put(CxField.CREATED_AT, CxDataSource.INTERNAL, ts)
      .put(CxField.UPDATED_AT, CxDataSource.INTERNAL, ts)
      .toString
  }

  private def timestamp(clock: Clock): LocalDateTime = {
    LocalDateTime.ofInstant(clock.instant(), ZoneId.systemDefault())
  }

  private def address(request: CxRequest) = CxFieldAccessor(addressCompExecutor.execute(request).data)
}