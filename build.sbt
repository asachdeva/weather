import com.scalapenos.sbt.prompt.SbtPrompt.autoImport._
import com.scalapenos.sbt.prompt._
import Dependencies._

// Reload Sbt on changes to sbt or dependencies
Global / onChangedBuildSource := ReloadOnSourceChanges

ThisBuild / scalaVersion := "2.13.5"
ThisBuild / startYear := Some(2021)
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "net.asachdeva"
ThisBuild / organizationName := "asachdeva"

ThisBuild / scalafixDependencies += Libraries.organizeImports
ThisBuild / semanticdbEnabled := true
ThisBuild / semanticdbVersion := scalafixSemanticdb.revision

val scalafixCommonSettings = inConfig(IntegrationTest)(scalafixConfigSettings(IntegrationTest))

promptTheme := PromptTheme(
  List(
    text(_ => "[weather-api]", fg(64)).padRight(" λ ")
  )
)

lazy val testSettings: Seq[Def.Setting[_]] = List(
  Test / parallelExecution := false,
  (publish / skip) := true,
  fork := true
)

lazy val noPublish = Seq(
  publish := {},
  publishLocal := {},
  publishArtifact := false,
  (publish / skip) := true
)

lazy val `weather` = project
  .in(file("."))
  .settings(
    testSettings,
    name := "weather-api",
    scalacOptions ++= List(
      "-Ymacro-annotations",
      "-Ywarn-unused:imports",
      "-Ywarn-unused:locals",
      "-Yrangepos",
      "-Wconf:cat=unused:info",
      "-Xmacro-settings:materialize-derivations"
    ),
    scalafmtOnCompile := true,
    resolvers += Resolver.sonatypeRepo("snapshots"),
    Defaults.itSettings,
    scalafixCommonSettings,
    libraryDependencies ++= Seq(
      CompilerPlugin.kindProjector,
      CompilerPlugin.betterMonadicFor,
      CompilerPlugin.semanticDB,
      Libraries.cats,
      Libraries.catsEffect,
      Libraries.circeCore,
      Libraries.circeGeneric,
      Libraries.circeParser,
      Libraries.circeRefined,
      Libraries.derevoCore,
      Libraries.derevoCats,
      Libraries.derevoCirce,
      Libraries.fs2,
      Libraries.fs2I0,
      Libraries.fs2Stream,
      Libraries.http4sDsl,
      Libraries.http4sServer,
      Libraries.http4sClient,
      Libraries.http4sCirce,
      Libraries.log4cats,
      Libraries.logback % Runtime,
      Libraries.newtype,
      Libraries.pureconfig,
      Libraries.refinedCore,
      Libraries.refinedCats,
      Libraries.refinedpureconfig
    )
  )

// Format and scalafix
addCommandAlias("format", ";scalafmtAll ;scalafmtSbt ;scalafixAll")

// CI build
addCommandAlias("buildWeather", ";clean;+test;")
