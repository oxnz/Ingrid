package io.github.oxnz.Ingrid.cx

class CxValue[T](val dataSource: CxDataSource, val value: T, val defaultValue: T) {
  require(value != null || defaultValue != null, "value and defaultValue cannot be null at the same time")

  override def toString = s"CxValue(value=$value, defaultValue=$defaultValue)"
}
