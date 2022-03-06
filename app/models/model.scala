
import play.api.libs.json.{Format, JsArray, Json}

import java.util.Date

package object models {
  case class Weather(condition: Option[String], temperature: Option[String], alerts: List[Alert])
  case class Alert(event: Option[String], start: Option[Date], end: Option[Date], description: Option[String], tags: List[String])
  case class IncomingAlert(sender_name: Option[String], event: Option[String], start: Option[Long], end: Option[Long], description: Option[String], tags: List[String])

  object Formatters {
    implicit val alertFormat = Json.format[Alert]
    implicit val incomingAlertFormat = Json.format[IncomingAlert]
    implicit val weatherFormat = Json.format[Weather]
  }
}
