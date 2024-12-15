package io.github.kory33.scala3_compiletime_bignat

infix type <::<[A, B] <: true | false = A match {
  case B => true
  case _ => false
}

infix type =::=[A, B] = scala.compiletime.ops.boolean.&&[A <::< B, B <::< A]

type IsSingletonLong[N] <: true | false = N match {
  case Singleton & Long => true
  case _                => false
}

type IfThenElse[Cond <: Boolean, Then, Else] <: Then | Else = Cond match {
  case true  => Then
  case false => Else
}

type If[Cond <: Boolean] = [Then, Else] =>> IfThenElse[Cond, Then, Else]

type BoolAsLong[B <: Boolean] = B match {
  case true  => 1L
  case false => 0L
}
