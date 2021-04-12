resolvers += Classpaths.sbtPluginReleases
resolvers += "Typesafe Repository".at("https://repo.typesafe.com/typesafe/releases/")

addSbtPlugin("ch.epfl.scala"             % "sbt-bloop"           % "1.4.8")
addSbtPlugin("ch.epfl.scala"             % "sbt-scalafix"        % "0.9.27")
addSbtPlugin("com.github.sbt"            % "sbt-pgp"             % "2.1.2")
addSbtPlugin("com.scalapenos"            % "sbt-prompt"          % "1.0.2")
addSbtPlugin("de.gccc.sbt"               % "sbt-jib"             % "0.8.0")
addSbtPlugin("io.github.davidgregory084" % "sbt-tpolecat"        % "0.1.17")
addSbtPlugin("org.scalameta"             % "sbt-scalafmt"        % "2.4.2")
