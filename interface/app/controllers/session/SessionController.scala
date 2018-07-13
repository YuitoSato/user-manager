package controllers.session

import commands.LoginCommand
import controllers.ControllerBase
import javax.inject.Inject
import play.api.libs.json.JsValue
import play.api.mvc.{ Action, ControllerComponents }
import usermanager.application.scenarios.session.SessionScenario
import usermanager.application.scenarios.user.UserScenario
import usermanager.domain.helpers.HashHelper
import usermanager.domain.result.ResultBuilder

import scala.concurrent.ExecutionContext

class SessionController @Inject()(
  val sessionScenario: SessionScenario,
  val userScenario: UserScenario,
)(
  implicit val ec: ExecutionContext,
  implicit val controllerComponents: ControllerComponents,
  implicit val resultBuilder: ResultBuilder,
  implicit val hashHelper: HashHelper
) extends ControllerBase {

  def create: Action[JsValue] = controllerComponents.actionBuilder.async(parse.json) { implicit req =>
    (for {
      login <- deserializeAsync[LoginCommand]
      user <- userScenario.findByEmail(login.email)
      _ <- resultBuilder.build(user.authenticate(login.password))
      _ <- sessionScenario.create(user.toSessionUser)
    } yield()).toAsyncMvcResult
  }

}
