ThisBuild / scalaVersion := "3.6.2"
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
ThisBuild / scalafixDependencies += "com.github.liancheng" %% "organize-imports" % "0.6.0"

ThisBuild / scalacOptions ++= Seq(
  "--deprecation",
  "--explain",
  "-Ykind-projector:underscores"
)

lazy val core =
  project.in(file("core")).settings(name := "compiletime-bignat-core")

// region publishing configuration

// TODO: add

// endregion
