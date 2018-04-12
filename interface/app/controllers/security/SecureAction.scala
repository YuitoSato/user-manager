package controllers.security

import javax.inject.Inject

import play.api.mvc._
import syntax.ToResultOps
import usermanager.application.scenarios.session.SessionScenario
import usermanager.domain.aggregates.sessionuser.SessionUser
import usermanager.domain.error.DomainError
import usermanager.domain.result.AsyncResult
import usermanager.domain.syntax.ToEitherOps

import scala.concurrent.{ ExecutionContext, Future }
import scalaz.syntax.std.ToOptionOps
import scalaz.{ -\/, EitherT, \/- }

case class SecureAction @Inject() (
  sessionScenario: SessionScenario
)(
  implicit val controllerComponents: ControllerComponents,
  implicit val ec: ExecutionContext
) extends ToResultOps with ToEitherOps with ToOptionOps with BaseControllerHelpers {

  def findUserBySession(req: Request[_]): EitherT[Future, DomainError, SessionUser] = {
    (for {
      key <- AsyncResult(req.session.get("session") \/> DomainError.BadRequest("session key is not found"))
      user <- sessionScenario.findById(key)
    } yield user).value
  }
//
//  def apply(requestHandler: SecureRequest[AnyContent] => Result): Future[Action[AnyContent]] = {
//    Future.successful(apply(controllerComponents.parsers.anyContent)(requestHandler))
//  }
//
//  def apply[A](bodyParser: BodyParser[A])(requestHandler: SecureRequest[A] => Result): Action[A] = {
//    controllerComponents.actionBuilder.apply(bodyParser) { req =>
//      findUserBySession(req) match {
//        case \/-(user) => requestHandler(SecureRequest(user, req))
//        case -\/(e: DomainError.BadRequest) => e.toResult
//        case -\/(_) => DomainError.Unauthorized.toResult
//      }
//    }
//  }

  def async(requestHandler: SecureRequest[AnyContent] => Future[Result]): Action[AnyContent] = {
    async(controllerComponents.parsers.anyContent)(requestHandler)
  }

  def async[A](bodyParser: BodyParser[A])(requestHandler: SecureRequest[A] => Future[Result]): Action[A] = {
    controllerComponents.actionBuilder.async(bodyParser) { req =>
      findUserBySession(req).run.flatMap {
        case \/-(user) => requestHandler(SecureRequest(user, req))
        case -\/(e: DomainError.BadRequest) => Future.successful(e.toResult)
        case -\/(_) => Future.successful(DomainError.Unauthorized.toResult)
      }
    }
  }

}
