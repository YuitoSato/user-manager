package controllers.session

import javax.inject.{ Inject, Singleton }

import play.api.mvc.ControllerComponents
import usermanager.application.scenarios.session.SessionScenario
import usermanager.application.scenarios.user.UserScenario
import usermanager.domain.helpers.HashHelper
import usermanager.domain.result.ResultBuilder

import scala.concurrent.ExecutionContext

@Singleton
class SessionControllerImpl @Inject()(
  val sessionScenario: SessionScenario,
  val userScenario: UserScenario,
)(
  implicit val ec: ExecutionContext,
  implicit val controllerComponents: ControllerComponents,
  implicit val resultBuilder: ResultBuilder,
  implicit val hashHelper: HashHelper
) extends SessionController
