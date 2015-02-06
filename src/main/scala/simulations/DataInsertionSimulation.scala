package simulations

import common._
import common.json._
import feeders._
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import org.joda.time._
import play.api.libs.json.Json
import scala.concurrent.duration._
import scala.util.Random

class DataInsertionSimulation extends io.gatling.core.scenario.Simulation {

  val teams = TeamFeeder.teams.random
  val metrics = MetricFeeder.metricDefinitions.random
  val repeats = 10

  val scn = scenario("BasicSimulation")
    .feed(teams)
    .feed(metrics)
    .repeat(repeats) {
    exec { session =>
      val metricName = session("metricName").as[String]
      val metricTags = session("metricTags").as[Map[String, String]]
      val timestamp = new DateTime(DateTimeZone.UTC).getMillis / 1000

      val requestBody = Json.obj("metrics" -> Json.toJson(Seq(Metric(metricName, metricTags, timestamp, Random.nextDouble)))).toString

      session.setAll(
        "requestUrl" -> TeamFeeder.teamUrl(session("teamName").as[String]),
        "requestBody" -> requestBody
      )
    }
    .exec {
      http("name")
        .post("${requestUrl}")
        .header("Authorization", "Bearer ${teamToken}")
        .header("Content-Type", "application/json")
        .header("Accept", "application/json")
        .body(StringBody(stringToExpression[String]("${requestBody}")))
    }
    .pause(60.seconds)
  }

  setUp(scn.inject(
    splitUsers(100) into (atOnceUsers(10)) separatedBy(6.seconds)
  ))
}
