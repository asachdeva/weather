package net
package asachdeva
package service

import cats.effect.IO
import eu.timepit.refined.types.string.NonEmptyString
import fs2._
import _root_.io.circe.{Decoder, Encoder}
import _root_.io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import _root_.io.circe.magnolia.configured.Configuration
import net.asachdeva.config.Config
import net.asachdeva.service.WeatherController.Report.reportEntityEncoder
import org.http4s.{EntityDecoder, EntityEncoder, HttpRoutes, Method, Request}
import org.http4s.circe.{jsonEncoderOf, jsonOf}
import org.http4s.implicits._
import org.http4s.dsl.io._
import org.http4s.ember.client.EmberClientBuilder

import scala.util.Try

case class Latitude(value: Double)  extends AnyVal
case class Longitude(value: Double) extends AnyVal

object WeatherController {
  final case class OpenWeatherError(e: Throwable) extends RuntimeException

  final case class Kelvin(value: Double) extends AnyVal
  final case class WeatherCondition(main: String, description: String)
  final case class CurrentConditions(feelsLike: Kelvin, weather: List[WeatherCondition])
  final case class Alert(description: String)
  final case class Status(current: CurrentConditions, alerts: List[Alert] = Nil)
  final case class Report(weatherConditions: List[String], summary: String, alerts: List[String])

  object Report {
    implicit val customConfig: Configuration                    = Configuration.default.withDefaults
    implicit val reportEncoder: Encoder[Report]                 = deriveEncoder
    implicit def reportEntityEncoder: EntityEncoder[IO, Report] = jsonEncoderOf
  }

  object Status {
    implicit val customConfig: Configuration                          = Configuration.default.withSnakeCaseMemberNames.withDefaults
    implicit val kelvinDecoder: Decoder[Kelvin]                       = deriveDecoder
    implicit val weatherConditionDecoder: Decoder[WeatherCondition]   = deriveDecoder
    implicit val currentConditionsDecoder: Decoder[CurrentConditions] = deriveDecoder
    implicit val alertDecoder: Decoder[Alert]                         = deriveDecoder
    implicit val statusDecoder: Decoder[Status]                       = deriveDecoder
    implicit def statusEntityDecoder: EntityDecoder[IO, Status]       = jsonOf
  }

  private val toComfort: Kelvin => String = {
    case Kelvin(x) if x < 294.0 => "Cold"
    case _                      => "Moderate"
  }
}

class WeatherController(config: Config) extends Controller[IO] {
  import net.asachdeva.service.WeatherController._

  object LatitudeVar {
    def unapply(s: String): Option[Latitude] = Try(Latitude(s.toDouble)).toOption
  }

  object LongitudeVar {
    def unapply(s: String): Option[Longitude] = Try(Longitude(s.toDouble)).toOption
  }

  val root: String = ""

  def get(apiToken: NonEmptyString, lat: Latitude, lon: Longitude) = {
    println("yo- again")
    val parameters = Map(
      "lat"     -> lat.value.toString,
      "lon"     -> lon.value.toString,
      "exclude" -> "minutely,hourly,daily",
      "appid"   -> apiToken.toString()
    )

    val weatherUri    = uri"https://api.openweathermap.org/data/2.5/onecall".withQueryParams(parameters)
    val weatherUriReq = Request[IO](Method.GET, weatherUri)
    EmberClientBuilder.default[IO].build.use { client =>
      Stream(
        client.fetchAs[Status](weatherUriReq).map { status =>
          Report(
            weatherConditions = status.current.weather.map { case WeatherCondition(main, description) =>
              s"$main: $description"
            },
            summary = toComfort(status.current.feelsLike),
            alerts = status.alerts.map(_.description)
          )
        }
      ).drain
    }
  }

  def routes: HttpRoutes[IO] =
    HttpRoutes.of[IO] { case GET -> Root / "weather" / "lat" / LatitudeVar(lat) / "lon" / LongitudeVar(lon) =>
      println("yo")
      get(config.token, lat, lon).flatMap(Ok(_))
    }

}
