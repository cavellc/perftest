package simulations

import common.Metric
import common.json._
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import org.joda.time.{DateTime, DateTimeZone}
import play.api.libs.json.{JsValue, Json}

import scala.util.Random

class DataInsertionSimulation extends io.gatling.core.scenario.Simulation {

  final val ApiUrl = "https://api-test.cavellc.io"
  final val Organization = "perftest"
  final val Token = "d43Zp5l96FKyHP8JFEpOxalJ4zDfHoclaLpNEdDZNExEFoA8Rl9di71E"

  final val MetricsUrl = s"$ApiUrl/organizations/$Organization/metrics"
  final val MetricsHeaders = Map(
    "Content-Type" -> "application/json",
    "Accept" -> "application/json",
    "Authorization" -> s"Bearer $Token"
  )


  val now = new DateTime(DateTimeZone.UTC).getMillis / 1000

  val metrics = randomMetrics(10, "orders", Map("shipTo" -> "US"), now)

  val createMetricsRequest = http("create-metrics")
    .post(MetricsUrl)
    .headers(MetricsHeaders)
    .body(StringBody(metrics.toString()))

  val scn = scenario("BasicSimulation").exec(createMetricsRequest).pause(5)

  setUp(scn.inject(atOnceUsers(1)))

  def randomMetrics(count: Int, name: String, tags: Map[String, String], timestamp: Long): JsValue = {

    Json.obj("metrics" -> Json.toJson(
      ((1 to count) map { _ =>
        Metric(name, tags, timestamp, Random.nextDouble())
      }).toSeq
    ))
  }
}
