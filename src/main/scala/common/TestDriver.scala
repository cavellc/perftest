package common

import com.typesafe.config._
import io.gatling.app.Gatling
import io.gatling.core.scenario.Simulation
import simulations.DataInsertionSimulation

object TestDriver extends App {

  val moreArgs = Array(
    "--no-reports",
    "--run-description", "DataInsertionSimulation",
    "--output-name", "results",
    "--mute")
  Gatling.fromArgs(args ++ moreArgs, Some(classOf[DataInsertionSimulation].asInstanceOf[Class[Simulation]]))

  new TestResult("./results", ConfigFactory.load())
}
