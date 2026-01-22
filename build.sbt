ThisBuild / scalaVersion := "3.8.1"
ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / versionScheme := Some("semver-spec")

ThisBuild / organization := "io.github.kory33"
ThisBuild / homepage := Some(
  url("https://github.com/kory33/scala3-compiletime-bignat")
)
// TODO: add ThisBuild / licenses
ThisBuild / developers := List(
  Developer(
    "kory33",
    "Ryosuke Kondo",
    "korygm33@gmail.com",
    url("https://github.com/kory33")
  )
)

ThisBuild / semanticdbEnabled := true

ThisBuild / scalacOptions ++= Seq(
  "--deprecation",
  "--explain",
  "-Xkind-projector:underscores"
)

lazy val core =
  project.in(file("core")).settings(name := "compiletime-bignat-core")

// region publishing configuration

// TODO: add

// endregion
