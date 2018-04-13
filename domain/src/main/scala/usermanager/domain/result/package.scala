package usermanager.domain

import usermanager.domain.error.DomainError
import usermanager.domain.syntax.ToEitherOps

import scala.concurrent.Future
import scalaz.{ -\/, EitherT, \/, \/- }

package object result {


  type Result[A] = EitherT[Future, DomainError, A]

  object Result extends ToEitherOps {

    def apply[A](value: DomainError \/ A): Result[A] = {
      Future.successful(value).et
    }

    def apply[A](value: A): Result[A] = {
      val either: DomainError \/ A = \/-(value)
      Future.successful(either).et
    }

    def error[A](error: DomainError): Result[A] = {
      val either: DomainError \/ A = -\/(error)
      Future.successful(either).et
    }
  }

}
