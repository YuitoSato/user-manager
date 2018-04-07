package usermanager.domain.result.async

import usermanager.domain.error.DomainError
import usermanager.domain.result.Result
import usermanager.domain.syntax.ToEitherOps

import scala.concurrent.{ ExecutionContext, Future }
import scalaz.std.FutureInstances
import scalaz.{ -\/, EitherT, \/, \/- }

case class AsyncResult[A](value: EitherT[Future, DomainError, A])(implicit ec: ExecutionContext) extends Result[A] with FutureInstances {


  override def map[B](f: A => B): AsyncResult[B] = AsyncResult(value.map(f))

  override def flatMap[B](f: A => Result[B]): AsyncResult[B] = {
    AsyncResult(value.flatMap(f(_).asInstanceOf[AsyncResult[B]].value))
  }

}

object AsyncResult extends ToEitherOps {

  def apply[A](value: A)(implicit ec: ExecutionContext): AsyncResult[A] = {
    val either: DomainError \/ A = \/-(value)
    AsyncResult(Future.successful(either).et)
  }

  def error[A](error: DomainError)(implicit ec: ExecutionContext): AsyncResult[A] = {
    val either: DomainError \/ A = -\/(error)
    AsyncResult(Future.successful(either).et)
  }
}
