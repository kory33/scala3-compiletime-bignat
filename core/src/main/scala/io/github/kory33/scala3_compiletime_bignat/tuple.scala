package io.github.kory33.scala3_compiletime_bignat

object tuple {
  type DropWhile[T <: Tuple, P[_] <: Boolean] <: Tuple =
    T match {
      case h *: t =>
        P[h] match {
          case true  => DropWhile[t, P]
          case false => h *: DropWhile[t, P]
        }
      case EmptyTuple => EmptyTuple
    }

  type DropLastWhile[T <: Tuple, P[_] <: Boolean] =
    Tuple.Reverse[DropWhile[Tuple.Reverse[T], P]]
}
