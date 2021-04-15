import sbt._

object Dependencies {

  object Versions {
    val cats       = "2.5.0"
    val catsEffect = "3.0.2"
    val circe      = "0.14.0-M5"
    val derevo     = "0.12.2"
    val fs2        = "3.0.1"
    val http4s     = "1.0.0-M21"
    val log4Cats   = "2.0.1"
    val newtype    = "0.4.4"
    val pureConfig = "0.14.1"
    val refined    = "0.9.23"

    val betterMonadicFor = "0.3.1"
    val kindProjector    = "0.11.3"
    val logback          = "1.2.3"
    val organizeImports  = "0.5.0"
    val semanticDB       = "4.4.13"

    val weaver = "0.7.1"
  }

  object Libraries {
    def circe(artifact: String): ModuleID   = "io.circe"   %% s"circe-$artifact"  % Versions.circe
    def derevo(artifact: String): ModuleID  = "tf.tofu"    %% s"derevo-$artifact" % Versions.derevo
    def fs2(artifact: String): ModuleID     = "co.fs2"     %% s"fs2-$artifact"    % Versions.fs2
    def http4s(artifact: String): ModuleID  = "org.http4s" %% s"http4s-$artifact" % Versions.http4s
    def refined(artifact: String): ModuleID = "eu.timepit" %% s"$artifact"        % Versions.refined

    val cats       = "org.typelevel" %% "cats-core"   % Versions.cats
    val catsEffect = "org.typelevel" %% "cats-effect" % Versions.catsEffect

    val circeCore    = circe("core")
    val circeParser  = circe("parser")
    val circeGeneric = circe("generic")
    val circeLiteral = circe("literal")
    val circeOptics  = circe("optics")
    val circeRefined = circe("refined")

    val derevoCore  = derevo("core")
    val derevoCats  = derevo("cats")
    val derevoCirce = derevo("circe-magnolia")

    val fs2       = "co.fs2" %% "fs2-core"             % Versions.fs2
    val fs2I0     = "co.fs2" %% "fs2-io"               % Versions.fs2
    val fs2Stream = "co.fs2" %% "fs2-reactive-streams" % Versions.fs2

    val http4sDsl    = http4s("dsl")
    val http4sServer = http4s("ember-server")
    val http4sClient = http4s("ember-client")
    val http4sCirce  = http4s("circe")

    val log4cats = "org.typelevel" %% "log4cats-slf4j" % Versions.log4Cats

    val newtype    = "io.estatico"           %% "newtype"    % Versions.newtype
    val pureconfig = "com.github.pureconfig" %% "pureconfig" % Versions.pureConfig

    val refinedCore       = refined("refined")
    val refinedCats       = refined("refined-cats")
    val refinedpureconfig = refined("refined-pureconfig")

    // Runtime
    lazy val logback = "ch.qos.logback" % "logback-classic" % Versions.logback

    // Test
    val log4catsNoOp = "org.typelevel"       %% "log4cats-noop"     % Versions.log4Cats
    val weaverTest   = "com.disneystreaming" %% "weaver-cats"       % Versions.weaver
    val weaverCheck  = "com.disneystreaming" %% "weaver-scalacheck" % Versions.weaver

    // Scalafix rules
    val organizeImports = "com.github.liancheng" %% "organize-imports" % Versions.organizeImports
  }

  object CompilerPlugin {

    val betterMonadicFor = compilerPlugin(
      "com.olegpy" %% "better-monadic-for" % Versions.betterMonadicFor
    )

    val kindProjector = compilerPlugin(
      "org.typelevel" % "kind-projector" % Versions.kindProjector cross CrossVersion.full
    )

    val semanticDB = compilerPlugin(
      "org.scalameta" % "semanticdb-scalac" % Versions.semanticDB cross CrossVersion.full
    )
  }

}
