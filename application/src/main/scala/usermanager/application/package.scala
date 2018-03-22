package usermanager

import usermanager.application.error.ApplicationError
import usermanager.application.error.ApplicationError.Unexpected

import scala.util.{ Failure, Success, Try }

package object application {

  implicit class TryOptionOps[T](maybeValue: Try[Option[T]]) {
    def ifNotExists(f: => ApplicationError)(): Either[ApplicationError, T] = {
      maybeValue match {
        case Success(Some(s)) => Right(s)
        case Success(None)    => Left(f)
        case Failure(e)       => Left(Unexpected(e))
      }
    }
  }

}
