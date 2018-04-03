package usermanager.domain.result.async

import usermanager.domain.error.DomainError
import usermanager.domain.result.Result
import usermanager.domain.syntax.ToEitherOps

import scala.concurrent.Future
import scalaz.{ -\/, EitherT, \/, \/- }

case class AsyncResult[+A](value: EitherT[Future, DomainError, A]) extends Result[A] {

  override def map[B](f: A => B): AsyncResult[B] = AsyncResult(value.map(f))

  override def flatMap[B](f: A => Result[B]): AsyncResult[B] = f(value).asInstanceOf[AsyncResult[B]]

}

object AsyncResult extends ToEitherOps {

  def apply[A](value: A): AsyncResult[A] = {
    val either: DomainError \/ A = \/-(value)
    AsyncResult(Future.successful(either).et)
  }

  def error[A](error: DomainError): AsyncResult[A] = {
    val either: DomainError \/ A = -\/(error)
    AsyncResult(Future.successful(either).et)
  }
}
