package syntax

import play.api.libs.json.{ Json, Writes }
import play.api.{ Logger, mvc }
import play.api.mvc.Results
import usermanager.domain.error.DomainError
import usermanager.domain.result.AsyncResult

import scala.concurrent.{ ExecutionContext, Future }
import scalaz.{ -\/, \/- }

trait ToResultOps extends Results {

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

  implicit class AsyncResultToResultOpts[A](asyncResult: AsyncResult[A]) {

    def toResult(implicit ec: ExecutionContext, writes: Writes[A]): Future[mvc.Result] = {
      toResult { value =>
        Ok(Json.toJson(value))
      }
    }

    def toResult(f: A => mvc.Result)(implicit ec: ExecutionContext): Future[mvc.Result] = {
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
