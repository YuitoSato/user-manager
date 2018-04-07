package controllers

import controllers.security.{ SecureAction, SecureRequest }
import play.api.libs.json._
import play.api.mvc.{ BaseController, ControllerComponents, Request }
import usermanager.application.services.session.SessionService
import usermanager.domain.error.DomainError
import usermanager.domain.sessionuser.SessionUser
import usermanager.domain.syntax.ToEitherOps
import usermanager.domain.transaction.sync.{ SyncTransactionBuilder, SyncTransactionRunner }

import scala.concurrent.ExecutionContext
import scalaz.{ EitherT, Monad, \/ }

trait ControllerBase extends BaseController with ToEitherOps { self =>

  def sessionService: SessionService

  implicit val ec: ExecutionContext
  implicit val syncTransactionRunner: SyncTransactionRunner
  implicit val syncTransactionBuilder: SyncTransactionBuilder
  implicit val controllerComponents: ControllerComponents

  implicit val unitWrites: Writes[Unit] = new Writes[Unit] {
    def writes(value: Unit): JsValue = JsNull
  }

  implicit def request2SessionUser(implicit r: SecureRequest[_]): SessionUser = r.sessionUser

  implicit val SecureAction: SecureAction = new SecureAction(sessionService = self.sessionService)

  def deserialize[A, F[_]](implicit req: Request[JsValue], reads: Reads[A], monad: Monad[F]): F[DomainError \/ A] = {
    val either = req.body.validate[A] match {
      case e: JsError => \/.left(DomainError.JsonError(e.toString))
      case s: JsSuccess[A] => \/.right(s.value)
    }
    monad.point(either)
  }

  def deserializeT[A, F[_]](implicit req: Request[JsValue], reads: Reads[A], monad: Monad[F]): EitherT[F, DomainError, A] = {
    deserialize(req, reads, monad).et
  }
}
