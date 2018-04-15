package syntax

import play.api.libs.json.{ Json, Writes }
import play.api.mvc.Results
import play.api.{ Logger, mvc }
import usermanager.domain.error.DomainError
import usermanager.domain.result.{ AsyncResult, Result, SyncResult }
import usermanager.domain.syntax.{ ToEitherOps, ToFutureOps }

import scala.concurrent.{ ExecutionContext, Future }
import scalaz.{ -\/, EitherT, \/- }

trait ToResultOps extends Results with ToFutureOps with ToEitherOps {

  val logger = Logger(this.getClass)

  implicit class ErrorToOps(error: DomainError) {

    def toResult: mvc.Result = InternalServerError(Json.obj(
      "code" -> error.code,
      "message" -> error.message
    ))
//      case Error.Unexpected(_) =>
//        logger.error(error.message)
//        InternalServerError(Json.obj(
//          "code" -> error.code,
//          "message" -> error.message
//        ))
//      case Error.Unauthorized => Unauthorized(Json.obj(
//        "code" -> Error.Unauthorized.code,
//        "message" -> Error.Unauthorized.message
//      ))
//      case Error.RecordNotFound(_) => NotFound(Json.obj(
//        "code" -> error.code,
//        "message" -> error.message
//      ))
//      case _ => BadRequest(Json.obj(
//        "code" -> error.code,
//        "message" -> error.message
//      ))
//    }
  }

  implicit class EitherTToResultOps[A](either: EitherT[Future, DomainError, A]) {

    implicit def to(implicit ec: ExecutionContext): AsyncResult[A] = AsyncResult(either)

  }

  implicit class ResultToResultOpts[A](result: Result[A]) {

    def toResult(implicit ec: ExecutionContext, writes: Writes[A]): Future[mvc.Result] = {
      toResult { value =>
        Ok(Json.toJson(value))
      }
    }

    def toResult(f: A => mvc.Result)(implicit ec: ExecutionContext): Future[mvc.Result] = {
      val asyncResult: AsyncResult[A] = result match {
        case sync: SyncResult[A] => AsyncResult(sync.value.future.et)
        case async: AsyncResult[A] => async
      }

      asyncResult.value.run
        .recover {
          case t =>
            -\/(DomainError.Unexpected(t))
        }
        .map {
          case \/-(a) => f(a)
          case -\/(e) => e.toResult
        }
    }
  }

}
