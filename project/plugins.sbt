resolvers += Resolver.sbtPluginRepo("releases")

addSbtPlugin("ch.epfl.scala"             % "sbt-scalafix" % "0.9.27")
addSbtPlugin("com.github.sbt"            % "sbt-pgp"      % "2.1.2")
addSbtPlugin("com.scalapenos"            % "sbt-prompt"   % "1.0.2")
addSbtPlugin("de.gccc.sbt"               % "sbt-jib"      % "0.8.0")
addSbtPlugin("io.github.davidgregory084" % "sbt-tpolecat" % "0.1.17")
addSbtPlugin("io.spray"                  % "sbt-revolver" % "0.9.1")
addSbtPlugin("org.scalameta"             % "sbt-scalafmt" % "2.4.2")
