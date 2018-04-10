package controllers.security

import javax.inject.Inject

import play.api.mvc._
import syntax.ToResultOps
import usermanager.application.scenarios.session.SessionScenario
import usermanager.domain.aggregates.sessionuser.SessionUser
import usermanager.domain.error.DomainError
import usermanager.domain.result.sync.SyncResult
import usermanager.domain.syntax.ToEitherOps

import scala.concurrent.Future
import scalaz.syntax.std.ToOptionOps
import scalaz.{ -\/, \/, \/- }

class SecureAction @Inject() (
  sessionScenario: SessionScenario
)(
  implicit val controllerComponents: ControllerComponents
) extends ToResultOps with ToEitherOps with ToOptionOps with BaseControllerHelpers {

  def findUserBySession(req: Request[_]): DomainError \/ SessionUser = {
    (for {
      key <- SyncResult(req.session.get("session") \/> DomainError.BadRequest("session key is not found"))
      user <- sessionScenario.awaitFindById(key)
    } yield user).value
  }

  def apply(requestHandler: SecureRequest[AnyContent] => Result): Action[AnyContent] = {
    apply(controllerComponents.parsers.anyContent)(requestHandler)
  }

  def apply[A](bodyParser: BodyParser[A])(requestHandler: SecureRequest[A] => Result): Action[A] = {
    controllerComponents.actionBuilder.apply(bodyParser) { req =>
      findUserBySession(req) match {
        case \/-(user) => requestHandler(SecureRequest(user, req))
        case -\/(e: DomainError.BadRequest) => e.toResult
        case -\/(_) => DomainError.Unauthorized.toResult
      }
    }
  }

  def async(requestHandler: SecureRequest[AnyContent] => Future[Result]): Action[AnyContent] = {
    async(controllerComponents.parsers.anyContent)(requestHandler)
  }

  def async[A](bodyParser: BodyParser[A])(requestHandler: SecureRequest[A] => Future[Result]): Action[A] = {
    controllerComponents.actionBuilder.async(bodyParser) { req =>
      findUserBySession(req) match {
        case \/-(user) => requestHandler(SecureRequest(user, req))
        case -\/(e: DomainError.BadRequest) => Future.successful(e.toResult)
        case -\/(_) => Future.successful(DomainError.Unauthorized.toResult)
      }
    }
  }

}
