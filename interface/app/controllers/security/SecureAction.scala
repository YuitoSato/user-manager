package controllers.security

import javax.inject.Inject

import play.api.mvc._
import syntax.ToResultOps
import usermanager.application.services.session.SessionService
import usermanager.domain.error.DomainError
import usermanager.domain.syntax.ToEitherOps

import scala.concurrent.Future
import scalaz.syntax.std.ToOptionOps
import scalaz.{ -\/, \/, \/- }

class SecureAction @Inject() (
  sessionService: SessionService
) extends ToResultOps with ToEitherOps with ToOptionOps {

  def findUserBySession(req: Request[_]): DomainError \/ Session = {
    for {
      key <- req.session.get("session") \/> DomainError.BadRequest("session key is not found")
      user <- sessionService.awaitFindById(key)
    } yield user
  }

  def apply(requestHandler: SecureRequest[AnyContent] => Result): Action[AnyContent] = {
    apply(BodyParsers.parse.anyContent)(requestHandler)
  }

  def apply[A](bodyParser: BodyParser[A])(requestHandler: SecureRequest[A] => Result): Action[A] = {
    Action(bodyParser) { req =>
      findUserBySession(req) match {
        case \/-(user) => requestHandler(SecureRequest(user, req))
        case -\/(e: DomainError.BadRequest) => e.toResult
        case -\/(_) => DomainError.Unauthorized.toResult
      }
    }
  }

  def async(requestHandler: SecureRequest[AnyContent] => Future[Result]): Action[AnyContent] = {
    async(BodyParsers.parse.anyContent)(requestHandler)
  }

  def async[A](bodyParser: BodyParser[A])(requestHandler: SecureRequest[A] => Future[Result]): Action[A] = {
    Action.async(bodyParser) { req =>
      findUserBySession(req) match {
        case \/-(user) => requestHandler(SecureRequest(user, req))
        case -\/(e: DomainError.BadRequest) => Future.successful(e.toResult)
        case -\/(_) => Future.successful(DomainError.Unauthorized.toResult)
      }
    }
  }
}
