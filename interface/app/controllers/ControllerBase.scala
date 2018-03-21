package controllers

import play.api.libs.json._
import play.api.mvc.{ Controller, Request }
import syntax.ToEitherOps
import usermanager.application.error.ApplicationError

import scala.concurrent.ExecutionContext
import scalaz.{ EitherT, Monad, \/ }

trait ControllerBase extends Controller with ToEitherOps { self =>

  def sessionService: SessionService

  implicit val ec: ExecutionContext

  implicit val unitWrites: Writes[Unit] = new Writes[Unit] {
    def writes(value: Unit): JsValue = JsNull
  }

  implicit def request2SessionUser(implicit r: SecureRequest[_]): User = r.user

  implicit val SecureAction: SecureAction = new SecureAction(sessionService = self.sessionService)

  def deserialize[A, F[_]](implicit req: Request[JsValue], reads: Reads[A], monad: Monad[F]): F[ApplicationError \/ A] = {
    val either = req.body.validate[A] match {
      case e: JsError => \/.left(ApplicationError.JsonError(e))
      case s: JsSuccess[A] => \/.right(s.value)
    }
    monad.point(either)
  }

  def deserializeT[A, F[_]](implicit req: Request[JsValue], reads: Reads[A], monad: Monad[F]): EitherT[F, ApplicationError, A] = {
    deserialize(req, reads, monad).et
  }
}
