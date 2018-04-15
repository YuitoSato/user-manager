package controllers.security

import javax.inject.Inject

import play.api.mvc._
import syntax.ToResultOps
import usermanager.application.scenarios.session.SessionScenario
import usermanager.domain
import usermanager.domain.aggregates.sessionuser.SessionUser
import usermanager.domain.error.DomainError
import usermanager.domain.result.{ AsyncResult, ResultBuilder }
import usermanager.domain.syntax.ToEitherOps

import scala.concurrent.{ ExecutionContext, Future }
import scalaz.std.FutureInstances
import scalaz.syntax.std.ToOptionOps
import scalaz.{ -\/, \/- }

case class SecureAction @Inject() (
  sessionScenario: SessionScenario
)(
  implicit val controllerComponents: ControllerComponents,
  implicit val ec: ExecutionContext,
  implicit val resultBuilder: ResultBuilder
) extends ToResultOps with ToEitherOps with ToOptionOps with BaseControllerHelpers with FutureInstances {

  def findUserBySession(req: Request[_]): domain.result.Result[SessionUser] = {
    for {
      key <- domain.result.Result(req.session.get("session") \/> DomainError.BadRequest("session key is not found"))
      user <- sessionScenario.findById(key)
    } yield user
  }

  def async(requestHandler: SecureRequest[AnyContent] => Future[Result]): Action[AnyContent] = {
    async(controllerComponents.parsers.anyContent)(requestHandler)
  }

  def async[A](bodyParser: BodyParser[A])(requestHandler: SecureRequest[A] => Future[Result]): Action[A] = {
    controllerComponents.actionBuilder.async(bodyParser) { req =>
      // TODO asInstanceOfは使いたくない
      findUserBySession(req).asInstanceOf[AsyncResult[SessionUser]].value.run.flatMap {
        case \/-(user) => requestHandler(SecureRequest(user, req))
        case -\/(e: DomainError.BadRequest) => Future.successful(e.toResult)
        case -\/(_) => Future.successful(DomainError.Unauthorized.toResult)
      }
    }
  }

}
