package controllers

import usermanager.application.scenarios.session.SessionScenario

import scalaz.Inject

class HealthCheckController @Inject()(
  val sessionScenario: SessionScenario
){

}
