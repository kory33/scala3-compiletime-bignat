package io.github.kory33.scala3_compiletime_bignat

sealed trait ComparisonResult
object ComparisonResult {
  case object Less extends ComparisonResult
  type Less = Less.type

  case object Equal extends ComparisonResult
  type Equal = Equal.type

  case object Greater extends ComparisonResult
  type Greater = Greater.type
}
