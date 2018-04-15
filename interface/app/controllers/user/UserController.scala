package controllers.user

import javax.inject.{ Inject, Named, Singleton }

import commands.UserCreateCommand
import controllers.ControllerBase
import play.api.libs.json.JsValue
import play.api.mvc.{ Action, ControllerComponents }
import syntax.ToResultOps
import usermanager.application.scenarios.session.SessionScenario
import usermanager.application.scenarios.user.UserScenario
import usermanager.domain.helpers.{ HashHelper, UUIDGenerator }
import usermanager.domain.result.ResultBuilder

import scala.concurrent.ExecutionContext
import scalaz.std.FutureInstances

@Singleton
class UserController @Inject()(
  userScenario: UserScenario,
  val sessionScenario: SessionScenario
)(
  implicit val ec: ExecutionContext,
  implicit val controllerComponents: ControllerComponents,
  implicit val uuidGenerator: UUIDGenerator,
  implicit val resultBuilder: ResultBuilder,
  @Named("bcrypt.mindrot") implicit val hashHelper: HashHelper
) extends ControllerBase with ToResultOps with FutureInstances {

  def create: Action[JsValue] = controllerComponents.actionBuilder.async(parse.json) { implicit req =>
    (for {
      user <- deserializeAsync[UserCreateCommand]
      _ <- userScenario.create(user.toEntity)
    } yield ()).toResult
  }
}
