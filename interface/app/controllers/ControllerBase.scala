package controllers

import controllers.security.{ SecureAction, SecureRequest }
import play.api.libs.json._
import play.api.mvc.{ BaseController, ControllerComponents, Request }
import syntax.ToResultOps
import usermanager.application.scenarios.session.SessionScenario
import usermanager.domain.aggregates.sessionuser.SessionUser
import usermanager.domain.error.Error
import usermanager.domain.result.{ AsyncResult, Result, ResultBuilder }
import usermanager.domain.syntax.ToEitherOps

import scala.concurrent.{ ExecutionContext, Future }
import scalaz.std.FutureInstances
import scalaz.{ Monad, \/ }

trait ControllerBase
  extends BaseController
    with ToEitherOps
    with ToResultOps
    with FutureInstances {
  self =>

  def sessionScenario: SessionScenario

  implicit val ec: ExecutionContext
  implicit val controllerComponents: ControllerComponents
  implicit val resultBuilder: ResultBuilder

  implicit val unitWrites: Writes[Unit] = new Writes[Unit] {
    def writes(value: Unit): JsValue = JsNull
  }

  implicit def request2SessionUser(implicit r: SecureRequest[_]): SessionUser = r.sessionUser

  implicit val SecureAction: SecureAction = new SecureAction(sessionScenario = self.sessionScenario)

  private def deserializeFuture[A](implicit req: Request[JsValue], reads: Reads[A], monad: Monad[Future]): Future[Error \/ A] = {
    val either = req.body.validate[A] match {
      case e: JsError => \/.left(Error.JsonError(e.toString))
      case s: JsSuccess[A] => \/.right(s.value)
    }
    monad.point(either)
  }

  def deserializeAsync[A](implicit req: Request[JsValue], reads: Reads[A], monad: Monad[Future]): Result[A] = {
    AsyncResult(deserializeFuture(req, reads, monad).et)
  }
}
