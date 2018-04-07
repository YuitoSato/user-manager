package controllers

import controllers.security.{ SecureAction, SecureRequest }
import play.api.libs.json._
import play.api.mvc.{ BaseController, ControllerComponents, Request }
import usermanager.application.scenarios.session.SessionScenario
import usermanager.domain.error.DomainError
import usermanager.domain.result.async.AsyncResult
import usermanager.domain.aggregates.sessionuser.SessionUser
import usermanager.domain.syntax.ToEitherOps

import scala.concurrent.{ ExecutionContext, Future }
import scalaz.{ Monad, \/ }

trait ControllerBase extends BaseController with ToEitherOps { self =>

  def sessionScenario: SessionScenario

  implicit val ec: ExecutionContext
  implicit val controllerComponents: ControllerComponents

  implicit val unitWrites: Writes[Unit] = new Writes[Unit] {
    def writes(value: Unit): JsValue = JsNull
  }

  implicit def request2SessionUser(implicit r: SecureRequest[_]): SessionUser = r.sessionUser

  implicit val SecureAction: SecureAction = new SecureAction(sessionScenario = self.sessionScenario)

  private def deserializeFuture[A](implicit req: Request[JsValue], reads: Reads[A], monad: Monad[Future]): Future[DomainError \/ A] = {
    val either = req.body.validate[A] match {
      case e: JsError => \/.left(DomainError.JsonError(e.toString))
      case s: JsSuccess[A] => \/.right(s.value)
    }
    monad.point(either)
  }

  def deserializeAsync[A](implicit req: Request[JsValue], reads: Reads[A], monad: Monad[Future]): AsyncResult[A] = {
    AsyncResult(deserializeFuture(req, reads, monad).et)
  }
}
