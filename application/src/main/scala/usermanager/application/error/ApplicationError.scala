package usermanager.application.error

import usermanager.domain.error.AbstractError

sealed trait ApplicationError extends AbstractError

object ApplicationError {

  case class Unexpected(msg: String) extends ApplicationError {
    val code = "error.unexpected"
    val message: String = msg
  }
  object Unexpected {
    def apply(t: Throwable): Unexpected = new Unexpected(t.toString)
  }

}
