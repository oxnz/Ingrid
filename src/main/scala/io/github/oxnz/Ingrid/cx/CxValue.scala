package io.github.oxnz.Ingrid.cx

case class CxValue[T](dataSource: CxDataSource, value: T, defaultValue: T) {
  require(value != null || defaultValue != null, "value and defaultValue cannot be null at the same time")

  override def toString = s"CxValue(value=$value, defaultValue=$defaultValue)"
}
