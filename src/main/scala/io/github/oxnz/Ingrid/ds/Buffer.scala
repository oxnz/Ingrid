package io.github.oxnz.Ingrid.ds

abstract class Buffer {
  type T
  val element: T
}

abstract class SeqBuffer extends Buffer {
  type U
  type T <: Seq[U]

  def length = element.length
}

class IPv4Buffer extends SeqBuffer {
  type IPv4 = Int
  override type U = IPv4
  override type T = List[U]
  override val element = List[IPv4](1)
}