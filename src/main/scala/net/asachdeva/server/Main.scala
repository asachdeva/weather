package net
package asachdeva
package server

import org.http4s.implicits._
import cats.effect.{ExitCode, IO, IOApp}
import com.comcast.ip4s.{Host, Port}
import net.asachdeva.config._
import net.asachdeva.service.WeatherController
import org.http4s.server.{Router, Server}
import org.http4s.server.defaults.Banner
import org.typelevel.log4cats.Logger
import org.typelevel.log4cats.slf4j.Slf4jLogger
import pureconfig._
import pureconfig.generic.auto._
import eu.timepit.refined.pureconfig._
import org.http4s.ember.server.EmberServerBuilder

object Main extends IOApp {
  implicit val logger = Slf4jLogger.getLogger[IO]

  implicit val configMaterializer: Derivation[ConfigReader[Config]] =
    Derivation.materializeDerivation[ConfigReader[Config]]

  def showEmberBanner(s: Server): IO[Unit] =
    Logger[IO].info(s"\n${Banner.mkString("\n")}\nHTTP Server (Ember) started at ${s.address}")

  def doRun(config: Config): IO[ExitCode] = {
    lazy val weatherApiController = new WeatherController(config)
    val app = Router(
      weatherApiController.root -> weatherApiController.routes
    ).orNotFound

    val http = EmberServerBuilder
      .default[IO]
      .withHost(Host.fromString(config.host).get)
      .withPort(Port.fromInt(config.port).get)
      .withHttpApp(app)
      .build
      .use(showEmberBanner(_) >> IO.never)
      .start

    for {
      _ <- http
      _ <- IO.never.as(())
    } yield ExitCode.Success
  }

  override def run(args: List[String]): IO[ExitCode] =
    ConfigSource.default.load[Config] match {
      case Left(failures) =>
        failures.toList.foreach(println _)
        IO(ExitCode.Error)
      case Right(config) => doRun(config)
    }
}
