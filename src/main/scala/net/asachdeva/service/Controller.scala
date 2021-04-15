package net
package asachdeva
package service

import eu.timepit.refined.types.string.NonEmptyString
import org.http4s.HttpRoutes

trait Controller[F[_]] {
  def get(apiToken: NonEmptyString, latitude: Latitude, longitude: Longitude): F[WeatherController.Report]
  val root: String
  def routes: HttpRoutes[F]
}
