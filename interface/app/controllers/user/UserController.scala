package controllers.user

import commands.UserCreateCommand
import controllers.ControllerBase
import play.api.libs.json.JsValue
import play.api.mvc._
import usermanager.application.scenarios.user.UserScenario
import usermanager.domain.helpers.{ HashHelper, UUIDGenerator }

trait UserController extends ControllerBase {

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
