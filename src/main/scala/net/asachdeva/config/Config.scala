package net
package asachdeva
package config

import eu.timepit.refined.types.string.NonEmptyString
import scala.concurrent.duration._

case class Config(
  host: String,
  port: Int,
  timeout: Duration = 1.minute,
  token: NonEmptyString,
  uri: String
)
