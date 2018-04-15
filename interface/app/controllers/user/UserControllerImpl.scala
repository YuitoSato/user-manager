package controllers.user

import javax.inject.{ Inject, Singleton }

import controllers.di.UserScenarioImpl
import play.api.mvc.ControllerComponents
import usermanager.application.scenarios.session.SessionScenario
import usermanager.domain.helpers.{ HashHelper, UUIDGenerator }
import usermanager.domain.result.ResultBuilder

import scala.concurrent.ExecutionContext

@Singleton
class UserControllerImpl @Inject()(
  val sessionScenario: SessionScenario
)(
  implicit val ec: ExecutionContext,
  implicit val controllerComponents: ControllerComponents,
  implicit val resultBuilder: ResultBuilder,
  implicit val uuidGenerator: UUIDGenerator,
  implicit val userScenario: UserScenarioImpl,
  implicit val hashHelper: HashHelper
) extends UserController
