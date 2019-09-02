package io.github.oxnz.Ingrid.tx

import org.springframework.boot.actuate.health.{Health, HealthIndicator}
import org.springframework.stereotype.Component

@Component
class TxHealthIndicator extends HealthIndicator {
  override def health(): Health = {
    Health.up().withDetail("txService", "up").build()
  }
}
