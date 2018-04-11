package controllers

import javax.inject.{ Inject, Named }

import commands.LoginCommand
import play.api.libs.json.JsValue
import play.api.mvc.{ Action, ControllerComponents }
import syntax.ToResultOps
import usermanager.application.scenarios.session.SessionScenario
import usermanager.application.scenarios.user.UserScenario
import usermanager.domain.helpers.HashHelper
import usermanager.domain.result.async.AsyncResult

import scala.concurrent.ExecutionContext
import scalaz.std.FutureInstances

class SessionController @Inject()(
  val sessionScenario: SessionScenario,
  userScenario: UserScenario,
)(
  implicit val ec: ExecutionContext,
  implicit val controllerComponents: ControllerComponents,
  @Named("bcrypt.mindrot") implicit val hashHelper: HashHelper
) extends ControllerBase with ToResultOps with FutureInstances {

  def create: Action[JsValue] = controllerComponents.actionBuilder.async(parse.json) { implicit req =>
    (for {
      login <- deserializeAsync[LoginCommand]
      user <- userScenario.findByEmail(login.email).toAsync
      _ <- AsyncResult(user.authenticate(login.password))
      _ <- sessionScenario.create(user.toSessionUser)
    } yield()).toResult
  }

}
