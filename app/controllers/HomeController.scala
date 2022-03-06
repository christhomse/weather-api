package controllers

import javax.inject._
import play.api.mvc._
import weather.WeatherClient
import models.Formatters._
import play.api.libs.json.Json

import scala.concurrent.ExecutionContext

class HomeController @Inject()(val controllerComponents: ControllerComponents, weatherClient: WeatherClient)(implicit ec: ExecutionContext) extends BaseController {

  def getWeather(lat: Double, long: Double) = Action.async { implicit request =>
    val weather = weatherClient.getWeatherFromOpenAPI(lat, long)
    weather.map{w => Ok(Json.toJson(w))}
  }
}
