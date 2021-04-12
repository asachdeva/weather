import sbt._

object Dependencies {

  object Versions {
    val circe = "0.13.0"
    val fs2 = "3.0.1"
        val http4s       = "0.21.22"
    val pureconfig   = "0.14.1"
      val rho                  = "0.21.0"

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
      def http4s(artifact: String, version: String): ModuleID      = "org.http4s"                   %% artifact % version
    
  lazy val circeCore            = circe("circe-core", Versions.circe)
  lazy val circeFs2             = circe("circe-parser", Versions.circe)
  lazy val circeGeneric         = circe("circe-generic", Versions.circe)
    lazy val circeLiteral       = circe("circe-literal", Versions.circe)
  lazy val circeOptics          = circe("circe-optics", Versions.circe)
  lazy val circeParser          = circe("circe-fs2", Versions.circe)
    lazy val fs2Core = fs2("fs2-core", Versions.fs2)
    lazy val fs2IO = fs2("fs2-io", Versions.fs2)
      lazy val http4sCirce          = http4s("http4s-circe", Versions.http4s)
  lazy val http4sDSL            = http4s("http4s-dsl", Versions.http4s)
  lazy val http4sServer         = http4s("http4s-blaze-server", Versions.http4s)
      lazy val http4sRho            = http4s("rho-swagger", Versions.rho)
  lazy val http4sServer         = http4s("rho-swagger-uir", Versions.rho)
    lazy val pureconfig           = "com.github.pureconfig"      %% "pureconfig"     % Versions.pureconfig

    
    // Compiler
    lazy val kindProjector = ("org.typelevel" %% "kind-projector" % Versions.kindProjector).cross(CrossVersion.full)
    lazy val betterMonadicFor = "com.olegpy" %% "better-monadic-for" % Versions.betterMonadicFor

    // Runtime
    lazy val logback = "ch.qos.logback" % "logback-classic" % Versions.logback

    // Test
  }

}
