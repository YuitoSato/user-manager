package controllers.user

import commands.UserCreateCommand
import controllers.ControllerBase
import play.api.libs.json.JsValue
import play.api.mvc._
import syntax.ToResultOps
import usermanager.application.scenarios.user.UserScenario
import usermanager.domain.helpers.{ HashHelper, UUIDGenerator }

import scalaz.std.FutureInstances

trait UserController extends ControllerBase with ToResultOps with FutureInstances {

  val userScenario: UserScenario
  implicit val uuidGenerator: UUIDGenerator
  implicit val hashHelper: HashHelper

  def create: Action[JsValue] = controllerComponents.actionBuilder.async(parse.json) { implicit req =>
    (for {
      user <- deserializeAsync[UserCreateCommand]
      _ <- userScenario.create(user.toEntity)
    } yield ()).toResult
  }
}
