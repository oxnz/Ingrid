package io.github.oxnz.Ingrid.expr

sealed abstract class Expr

case class Var(name: String) extends Expr
case class V(num: Int) extends Expr

case class UniExpr(op: String, arg: Expr) extends Expr

case class BinExpr(op: String, left: Expr, right: Expr) extends Expr {
  override def toString = s"BinExpr(op=$op, left=$left, right=$right)"

}

object Calculator {
  var vmap = Map("0" -> 0)
  def eval(expr: Expr) : Int = expr match {
    case V(n) => n
    case UniExpr("-", e) => -eval(e)
    case UniExpr("+", e) => eval(e)
    case BinExpr("+", left, right) => eval(left) + eval(right)
    case BinExpr("-", left, right) => eval(left) - eval(right)
    case _ => throw new IllegalArgumentException()
  }
}