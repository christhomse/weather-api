package weather

import models.{Alert, IncomingAlert, Weather}
import models.Formatters._
import play.api.libs.json.JsArray

import javax.inject._
import play.api.libs.ws.WSClient

import java.util.Date
import scala.concurrent.{ExecutionContext, Future}

class WeatherClient @Inject()(ws: WSClient)(implicit ec: ExecutionContext) {

  def getWeatherFromOpenAPI(lat: Double, long: Double): Future[Weather] ={
    ws.url(s"https://api.openweathermap.org/data/2.5/onecall?lat=${lat}&lon=${long}&exclude=minutely,hourly,daily&units=imperial&appid=3520829eb023fb06e405220cf7c375b4")
      .get().map { response =>
      if(response.status == 200) {
        val currentDate = new Date()
        val incomingAlerts = (response.json \ "alerts").asOpt[List[IncomingAlert]]
        val tempOption = (response.json \ "current" \ "temp").asOpt[Double]
        val conditionOption = (response.json \ "current" \ "weather"\ 0 \ "description").asOpt[String]
        val tempString = tempOption.map{temp =>
          if(temp < 60.0) {
            "cold"
          } else if (temp >= 60.0 && temp <= 80.0) {
            "moderate"
          } else {
            "Hot"} }
        val alerts = incomingAlerts.getOrElse(List.empty).map { alert => Alert(alert.event, alert.start.map{ startDate => new Date(startDate * 1000)}, alert.end.map{endDate => new Date(endDate * 1000)}, alert.description, alert.tags)}
        Weather(conditionOption, tempString, alerts.filter(_.start.getOrElse(new Date(0L)).before(currentDate)).filter(_.end.getOrElse(new Date(0L)).after(currentDate)))
      } else
        throw new Throwable("bad request")
    }
  }
}
