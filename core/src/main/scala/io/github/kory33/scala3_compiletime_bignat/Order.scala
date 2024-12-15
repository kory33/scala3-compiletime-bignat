package io.github.kory33.scala3_compiletime_bignat

sealed trait Order
object Order {
  case object Less extends Order
  type Less = Less.type

  case object Equal extends Order
  type Equal = Equal.type

  case object Greater extends Order
  type Greater = Greater.type
}
