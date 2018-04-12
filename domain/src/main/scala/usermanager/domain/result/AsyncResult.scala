package usermanager.domain.result

import usermanager.domain.error.DomainError
import usermanager.domain.syntax.ToEitherOps

import scala.concurrent.{ ExecutionContext, Future }
import scalaz.std.FutureInstances
import scalaz.{ -\/, EitherT, \/, \/- }

case class AsyncResult[A](value: EitherT[Future, DomainError, A])(implicit ec: ExecutionContext) extends FutureInstances {

  def map[B](f: A => B): AsyncResult[B] = AsyncResult(value.map(f))

  def flatMap[B](f: A => AsyncResult[B]): AsyncResult[B] = {
    AsyncResult(value.flatMap(f(_).value))
  }

}

object AsyncResult extends ToEitherOps {

  def apply[A](value: DomainError \/ A)(implicit ec: ExecutionContext): AsyncResult[A] = {
    AsyncResult(Future.successful(value).et)
  }

  def apply[A](value: A)(implicit ec: ExecutionContext): AsyncResult[A] = {
    val either: DomainError \/ A = \/-(value)
    AsyncResult(Future.successful(either).et)
  }

  def error[A](error: DomainError)(implicit ec: ExecutionContext): AsyncResult[A] = {
    val either: DomainError \/ A = -\/(error)
    AsyncResult(Future.successful(either).et)
  }
}
