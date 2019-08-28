package io.github.oxnz.Ingrid.tx

class TxRequest(val ref: String, val cat: TxCategory, val state: String, val city: String) {
  require(ref != null, "ref is required")
  require(cat != null, "cat is required")
  require(state != null, "state is required")
  require(city != null, "city is required")
}
