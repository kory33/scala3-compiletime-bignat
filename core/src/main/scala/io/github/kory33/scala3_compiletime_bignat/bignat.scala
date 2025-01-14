package io.github.kory33.scala3_compiletime_bignat

import scala.compiletime.ops.boolean._

object NotABigNat
type NotABigNat = NotABigNat.type

// We encode bignaturals as tuples of longs, with the
// least significant digits (64-bits) at the head of the tuple.

type RepresentsBigNat[N <: Tuple] <: Boolean = N match {
  case EmptyTuple => true
  case n *: tail  => IsSingletonLong[n] && RepresentsBigNat[tail]
}

object ops {
  import scala.compiletime.ops.boolean._
  import scala.compiletime.ops.int as IntOps
  import scala.compiletime.ops.long as LongOps

  type RemoveLeadingZeros[N <: Tuple] = tuple.DropLastWhile[N, [X] =>> X <::< 0L]

  type compare_impl[
      A <: Tuple /* leading zeros removed */,
      B <: Tuple /* leading zeros removed */
  ] <: ComparisonResult =
    (A, B) match {
      case (a *: atail, b *: btail) =>
        compare_impl[atail, btail] match {
          case ComparisonResult.Less    => ComparisonResult.Less
          case ComparisonResult.Equal   => unsignedlong.compare[a, b]
          case ComparisonResult.Greater => ComparisonResult.Greater
        }

      case (EmptyTuple, b *: _)     => ComparisonResult.Less
      case (a *: _, EmptyTuple)     => ComparisonResult.Greater
      case (EmptyTuple, EmptyTuple) => ComparisonResult.Equal
    }

  type compare[A <: Tuple, B <: Tuple] =
    compare_impl[RemoveLeadingZeros[A], RemoveLeadingZeros[B]]

  infix type <[A <: Tuple, B <: Tuple] = compare[A, B] <::< ComparisonResult.Less
  infix type >[A <: Tuple, B <: Tuple] = compare[A, B] <::< ComparisonResult.Greater
  infix type <=[A <: Tuple, B <: Tuple] = (A < B) || (A <::< B)
  infix type >=[A <: Tuple, B <: Tuple] = (A > B) || (A <::< B)

  type carried_+[A <: Tuple, B <: Tuple, Carry <: Boolean] <: Tuple = (A, B, Carry) match {
    case (a *: atail, b *: btail, carry) =>
      unsignedlong.full_+[a, b, carry] *: carried_+[
        atail,
        btail,
        unsignedlong.full_+[a, b, carry]
      ]
    case (EmptyTuple, b *: btail, carry) =>
      unsignedlong.semi_+[BoolAsLong[carry], b] *: carried_+[
        EmptyTuple,
        btail,
        unsignedlong.semi_+[BoolAsLong[carry], b]
      ]
    case (a *: atail, EmptyTuple, carry) =>
      unsignedlong.semi_+[BoolAsLong[carry], a] *: carried_+[
        atail,
        EmptyTuple,
        unsignedlong.semi_+[BoolAsLong[carry], a]
      ]
    case (EmptyTuple, EmptyTuple, false) => EmptyTuple
    case (EmptyTuple, EmptyTuple, true)  => 1L *: EmptyTuple
  }

  infix type +[A <: Tuple, B <: Tuple] = carried_+[A, B, false]
}
