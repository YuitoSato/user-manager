package controllers

import com.google.inject.Inject
import commands.LoginCommand
import play.api.libs.json.JsValue
import play.api.mvc.{ Action, ControllerComponents }
import syntax.ToResultOps
import usermanager.application.scenarios.session.SessionScenario
import usermanager.application.scenarios.user.UserScenario

import scala.concurrent.ExecutionContext
import scalaz.std.FutureInstances

class SessionController @Inject()(
  val sessionScenario: SessionScenario,
  userScenario: UserScenario
)(
  implicit val ec: ExecutionContext,
  implicit val controllerComponents: ControllerComponents
) extends ControllerBase with ToResultOps with FutureInstances {

  def create: Action[JsValue] = controllerComponents.actionBuilder.async(parse.json) { implicit req =>
    (for {
      login <- deserializeAsync[LoginCommand]
      user <- userScenario.findByEmail(login.email)
      _ <- sessionScenario.create(user.toSessionUser)
    } yield()).toResult
  }

}
