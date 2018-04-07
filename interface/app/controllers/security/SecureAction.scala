package controllers.security

import javax.inject.Inject

import play.api.mvc._
import syntax.ToResultOps
import usermanager.application.services.session.SessionService
import usermanager.domain.error.DomainError
import usermanager.domain.sessionuser.SessionUser
import usermanager.domain.syntax.ToEitherOps
import usermanager.domain.transaction.sync.{ SyncTransactionBuilder, SyncTransactionRunner }

import scala.concurrent.Future
import scalaz.syntax.std.ToOptionOps
import scalaz.{ -\/, \/, \/- }

class SecureAction @Inject() (
  sessionService: SessionService
)(
  implicit syncTransactionBuilder: SyncTransactionBuilder,
  implicit val syncTransactionRunner: SyncTransactionRunner,
  implicit val controllerComponents: ControllerComponents
) extends ToResultOps with ToEitherOps with ToOptionOps with BaseControllerHelpers {

  def findUserBySession(req: Request[_]): DomainError \/ SessionUser = {
    (for {
      key <- syncTransactionBuilder.exec(req.session.get("session") \/> DomainError.BadRequest("session key is not found"))
      user <- sessionService.awaitFindById(key)
    } yield user).run.value
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
