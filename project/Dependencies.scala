import sbt._

object Dependencies {

  object Versions {
    val circe = "0.13.0"
    val fs2 = "3.0.1"

    // Test

    // Compiler
    val kindProjector = "0.11.0"
    val betterMonadicFor = "0.3.1"

    // Runtime
    val logback = "1.2.3"
  }

  object Libraries {
    def circe(artifact: String, version: String): ModuleID = "io.circe" %% artifact % version
    def fs2(artifact: String, version: String): ModuleID = "co.fs2" %% artifact % version

    lazy val fs2Core = fs2("fs2-core", Versions.fs2)
    lazy val fs2IO = fs2("fs2-io", Versions.fs2)
    lazy val circeCore = circe("circe-core", Versions.circe)
    lazy val circeParser = circe("circe-parser", Versions.circe)
    lazy val circeGeneric = circe("circe-generic", Versions.circe)

    // Compiler
    lazy val kindProjector = ("org.typelevel" %% "kind-projector" % Versions.kindProjector).cross(CrossVersion.full)
    lazy val betterMonadicFor = "com.olegpy" %% "better-monadic-for" % Versions.betterMonadicFor

    // Runtime
    lazy val logback = "ch.qos.logback" % "logback-classic" % Versions.logback

    // Test
  }

}
