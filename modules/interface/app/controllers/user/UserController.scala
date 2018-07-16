package controllers.user

import commands.UserCreateCommand
import controllers.ControllerBase
import javax.inject.Inject
import play.api.libs.json.JsValue
import play.api.mvc._
import serializers.UserViewSerializer
import usermanager.application.scenarios.session.SessionScenario
import usermanager.domain.helpers.{ HashHelper, UUIDGenerator }
import usermanager.lib.result.ResultBuilder
import usermanager.query.user.UserQuery

import scala.concurrent.ExecutionContext

class UserController @Inject()(
  val sessionScenario: SessionScenario,
  val userScenario: UserScenarioImpl,
  val userQuery: UserQuery
)(
  implicit val ec: ExecutionContext,
  implicit val controllerComponents: ControllerComponents,
  implicit val resultBuilder: ResultBuilder,
  implicit val uuidGenerator: UUIDGenerator,
  implicit val hashHelper: HashHelper
) extends ControllerBase with UserViewSerializer {

  def find(userId: String): Action[AnyContent] = controllerComponents.actionBuilder.async { implicit req =>
    userQuery.findById(userId).toAsyncMvcResult
  }

  def create: Action[JsValue] = controllerComponents.actionBuilder.async(parse.json) { implicit req =>
    (for {
      user <- deserializeAsync[UserCreateCommand]
      _ <- userScenario.create(user.toEntity)
    } yield ()).toAsyncMvcResult
  }

}
