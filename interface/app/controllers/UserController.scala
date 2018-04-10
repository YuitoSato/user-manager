package controllers

import javax.inject.{ Inject, Named }

import commands.UserCreateCommand
import play.api.libs.json.JsValue
import play.api.mvc.{ Action, ControllerComponents }
import syntax.ToResultOps
import usermanager.application.scenarios.session.SessionScenario
import usermanager.application.scenarios.user.UserScenario
import usermanager.domain.helpers.{ HashHelper, UUIDGenerator }

import scala.concurrent.ExecutionContext
import scalaz.std.FutureInstances

class UserController @Inject()(
  userScenario: UserScenario,
  val sessionScenario: SessionScenario
)(
  implicit val ec: ExecutionContext,
  implicit val controllerComponents: ControllerComponents,
  implicit val uuidGenerator: UUIDGenerator,
  @Named("bcrypt.mindrot") implicit val hashHelper: HashHelper
) extends ControllerBase with ToResultOps with FutureInstances {

  def create: Action[JsValue] = controllerComponents.actionBuilder.async(parse.json) { implicit req =>
    (for {
      user <- deserializeAsync[UserCreateCommand]
      _ <- userScenario.create(user.toEntity).toAsync
    } yield ()).toResult
  }
}
