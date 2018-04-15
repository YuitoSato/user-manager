package controllers.session

import commands.LoginCommand
import controllers.ControllerBase
import play.api.libs.json.JsValue
import play.api.mvc.Action
import usermanager.application.scenarios.user.UserScenario
import usermanager.domain.helpers.HashHelper
import usermanager.domain.result.Result

trait SessionController extends ControllerBase {

  val userScenario: UserScenario
  implicit val hashHelper: HashHelper

  def create: Action[JsValue] = controllerComponents.actionBuilder.async(parse.json) { implicit req =>
    (for {
      login <- deserializeAsync[LoginCommand]
      user <- userScenario.findByEmail(login.email)
      _ <- Result(user.authenticate(login.password))
      _ <- sessionScenario.create(user.toSessionUser)
    } yield()).toResult
  }

}
