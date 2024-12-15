package io.github.kory33.scala3_compiletime_bignat

// NOTE: Implementations of unsignedlong types are defined here with different names
//       to avoid ambiguity with scala.compiletime.ops.long types
object unsignedlong_definitions {
  import scala.compiletime.ops.boolean._
  import scala.compiletime.ops.long._

  infix type impl_<[A <: Long, B <: Long] =
    (A >= 0L && B >= 0L && A < B) || // if both are nonnegative and A < B
      (A < 0L && B >= 0L) || // if only A is negative
      // if only B is negative, then A is not (unsignedly) less than B
      (A < 0L && B < 0L && A > B) // if both are negative and A > B

  infix type impl_semi_+[A <: Long, B <: Long] = A + B
  infix type impl_carry_semi_+[A <: Long, B <: Long] =
    (A >= 0L && B >= 0L) || // if both are nonnegative
      (A < 0L && B >= 0L && (B < (0L - A))) || // if only A is negative and B is small enough to not cause carry
      (A >= 0L && B < 0L && (A < (0L - B))) // if only B is negative and A is small enough to not cause carry

  infix type impl_carry_full_+[A <: Long, B <: Long, Carry <: Boolean] <: Boolean =
    (Carry, A, B) match {
      case (false, a, b) => a impl_carry_semi_+ b
      case (true, a, b)  => (a <::< -1L) || ((a + 1L) impl_carry_semi_+ b)
    }
}

object unsignedlong {
  import scala.compiletime.ops.boolean._

  infix type <[A <: Long, B <: Long] = unsignedlong_definitions.impl_<[A, B]
  infix type <=[A <: Long, B <: Long] = A <::< B || A < B
  infix type >[A <: Long, B <: Long] = B < A
  infix type >=[A <: Long, B <: Long] = B <= A

  // semi-addition
  infix type semi_+[A <: Long, B <: Long] = unsignedlong_definitions.impl_semi_+[A, B]
  infix type carry_semi_+[A <: Long, B <: Long] =
    unsignedlong_definitions.impl_carry_semi_+[A, B]

  // full-addition
  type full_+[A <: Long, B <: Long, Carry <: Boolean] =
    (Carry, A, B) match {
      case (false, a, b) => a semi_+ b
      case (true, a, b)  => a semi_+ b semi_+ 1L
    }
  type carry_full_+[A <: Long, B <: Long, Carry <: Boolean] =
    unsignedlong_definitions.impl_carry_full_+[A, B, Carry]

  type compare[A <: Long, B <: Long] =
    If[A < B][Order.Less, If[A > B][Order.Greater, Order.Equal]]
}
