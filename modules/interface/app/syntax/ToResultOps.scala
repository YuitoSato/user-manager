package syntax

import error.InterfaceError
import play.api.libs.json.{ JsObject, Json, Writes }
import play.api.mvc.Results
import play.api.{ Logger, mvc }
import scalaz.{ -\/, EitherT, \/- }
import usermanager.application.error.ApplicationError
import usermanager.domain.error.DomainError
import usermanager.domain.syntax.{ ToEitherOps, ToFutureOps }
import usermanager.lib.error.Error
import usermanager.lib.result.{ AsyncResult, Result, SyncResult }

import scala.concurrent.duration.Duration
import scala.concurrent.{ Await, ExecutionContext, Future }

trait ToResultOps extends Results with ToFutureOps with ToEitherOps {

  val logger = Logger(this.getClass)

  implicit class ErrorToOps(error: Error) {
    def toResult: mvc.Result = error match {
      case InterfaceError.SessionKeyNotFound => BadRequest(toJson)
      case _: InterfaceError.JsonReadsError  => BadRequest(toJson)
      case DomainError.Unauthorized          => Unauthorized(toJson)
      case _: ApplicationError.NotFound      => NotFound(toJson)
      case ApplicationError.EmailExists      => Conflict(toJson)
      case ApplicationError.EmailNotFound    => Unauthorized(toJson)
      case _                                 => InternalServerError(toJson)
    }

    private def toJson: JsObject = Json.obj(
      "code" -> error.code,
      "message" -> error.message
    )
  }

  implicit class EitherTToResultOps[A](either: EitherT[Future, Error, A]) {

    implicit def to(implicit ec: ExecutionContext): AsyncResult[A] = AsyncResult(either)

  }

  implicit class ResultToResultOpts[A](result: Result[A]) {

    def toAsyncMvcResult(implicit ec: ExecutionContext, writes: Writes[A]): Future[mvc.Result] = {
      handleErrorAsync { value =>
        Ok(Json.toJson(value))
      }
    }

    def handleErrorAsync(fun: A => mvc.Result)(implicit ec: ExecutionContext): Future[mvc.Result] = {
      val asyncResult: AsyncResult[A] = result match {
        case sync: SyncResult[A] => AsyncResult(sync.value.future.et)
        case async: AsyncResult[A] => async
      }

      asyncResult.value.run
        .recover {
          case t =>
            -\/(Error.Unexpected(t))
        }
        .map {
          case \/-(a) => fun(a)
          case -\/(e) => e.toResult
        }
    }

    def toSyncMvcResult(implicit writes: Writes[A]): mvc.Result = {
      handleErrorSync { value =>
        Ok(Json.toJson(value))
      }
    }

    def handleErrorSync(fun: A => mvc.Result): mvc.Result = {
      val syncResult: SyncResult[A] = result match {
        case sync: SyncResult[A] => sync
        case async: AsyncResult[A] => SyncResult(Await.result(async.value.run, Duration.Inf))
      }

      syncResult.value
        match {
          case \/-(a) => fun(a)
          case -\/(e) => e.toResult
        }
    }

  }

}
