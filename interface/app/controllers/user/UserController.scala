package controllers.user

import commands.UserCreateCommand
import controllers.ControllerBase
import javax.inject.Inject
import play.api.libs.json.JsValue
import play.api.mvc._
import usermanager.application.scenarios.session.SessionScenario
import usermanager.domain.helpers.{ HashHelper, UUIDGenerator }
import usermanager.domain.result.ResultBuilder

import scala.concurrent.ExecutionContext

class UserController @Inject()(
  val sessionScenario: SessionScenario
)(
  implicit val ec: ExecutionContext,
  implicit val controllerComponents: ControllerComponents,
  implicit val resultBuilder: ResultBuilder,
  implicit val uuidGenerator: UUIDGenerator,
  implicit val userScenario: UserScenarioImpl,
  implicit val hashHelper: HashHelper
) extends ControllerBase {

  def create: Action[JsValue] = controllerComponents.actionBuilder.async(parse.json) { implicit req =>
    (for {
      user <- deserializeAsync[UserCreateCommand]
      _ <- userScenario.create(user.toEntity)
    } yield ()).toResult
  }

}
