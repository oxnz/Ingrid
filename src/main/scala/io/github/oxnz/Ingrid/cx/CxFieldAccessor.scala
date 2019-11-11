package io.github.oxnz.Ingrid.cx

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import org.springframework.lang.Nullable

import scala.jdk.CollectionConverters._

object CxFieldAccessor {
  final private val OBJECT_MAPPER = new ObjectMapper().enableDefaultTyping
    .registerModule(new DefaultScalaModule)
    .registerModule(new ParameterNamesModule)
    .registerModule(new Jdk8Module)
    .registerModule(new JavaTimeModule)

  def apply(string: String): CxFieldAccessor = new CxFieldAccessor(OBJECT_MAPPER.readTree(string).asInstanceOf[ObjectNode])
}

class CxFieldAccessor private(final private val root: ObjectNode) {

  import CxFieldAccessor._

  def this() = this(CxFieldAccessor.OBJECT_MAPPER.createObjectNode())

  def has(field: CxField): Boolean = root.has(field.name)

  def size: Int = root.size

  def isEmpty: Boolean = size == 0

  def nonEmpty: Boolean = !isEmpty

  def put(accessor: CxFieldAccessor): CxFieldAccessor = {
    root.setAll(accessor.root)
    this
  }

  def put[T](field: CxField, accessor: CxFieldAccessor): CxFieldAccessor = {
    if (accessor.nonEmpty) root.set(field.name, accessor.root)
    this
  }

  def put[T](field: CxField, accessors: java.util.List[CxFieldAccessor]): CxFieldAccessor = put(field, accessors.asScala.toList)

  def put[T](field: CxField, accessors: List[CxFieldAccessor]): CxFieldAccessor = {
    val objectNodes = accessors.filter(a => a.nonEmpty).map(_.root)
    if (objectNodes.nonEmpty) root.putArray(field.name).addAll(objectNodes.asJavaCollection)
    this
  }

  def put[T](field: CxField, dataSource: CxDataSource, @Nullable value: T): CxFieldAccessor = put(field, dataSource, value, null)

  def put[T](field: CxField, dataSource: CxDataSource, @Nullable value: T, @Nullable defaultValue: T): CxFieldAccessor = put(field, CxValue[T](dataSource, value, defaultValue))

  def put[T](field: CxField, value: CxValue[T]): CxFieldAccessor = {
    root.set(field.name, OBJECT_MAPPER.valueToTree(value))
    this
  }

  private def opt(field: CxField) = Some(field).filter(has).map(f => root.get(f.name))

  private def optValue[T](field: CxField, clazz: Class[T]): Option[T] = Some(field).filter(has).map(f => OBJECT_MAPPER.treeToValue(root.get(f.name()), clazz))

  def accessor(field: CxField): Option[CxFieldAccessor] = opt(field).map(n => new CxFieldAccessor(n.asInstanceOf[ObjectNode]))

  def accessors(field: CxField): List[CxFieldAccessor] = opt(field).getOrElse(OBJECT_MAPPER.createArrayNode()).asScala.toList.map(obj => new CxFieldAccessor(obj.asInstanceOf[ObjectNode]))

  def optValue[T](field: CxField): Option[CxValue[T]] = optValue(field, classOf[CxValue[T]])

  def value[T](field: CxField): Option[T] = optValue(field).map((v: CxValue[T]) => v.value).filter(_ != null)

  def defaultValue[T](field: CxField): Option[T] = optValue(field).map((v: CxValue[T]) => v.defaultValue).filter(_ != null)

  def valueOrDefault[T](field: CxField): T = value(field).orElse(defaultValue(field)).get

  def valueOrDefault[T](field: CxField, @Nullable defaultValue: T): T = value(field).getOrElse(defaultValue)

  override def toString: String = OBJECT_MAPPER.writerWithDefaultPrettyPrinter.writeValueAsString(root)

}