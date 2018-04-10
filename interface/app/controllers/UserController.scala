package controllers

import javax.inject.Inject

import play.api.mvc.ControllerComponents
import usermanager.application.scenarios.session.SessionScenario
import usermanager.application.scenarios.user.UserScenario

import scala.concurrent.ExecutionContext

class UserController @Inject()(
  userScenario: UserScenario,
  val sessionScenario: SessionScenario
)(
  implicit val ec: ExecutionContext,
  implicit val controllerComponents: ControllerComponents
) extends ControllerBase {

  def create() = ???
}
