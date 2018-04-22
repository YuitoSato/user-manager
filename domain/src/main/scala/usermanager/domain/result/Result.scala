package usermanager.domain.result

import usermanager.domain.error.DomainError
import usermanager.domain.syntax.{ ToEitherOps, ToFutureOps }

import scala.concurrent.duration.Duration
import scala.concurrent.{ Await, ExecutionContext, Future }
import scalaz.std.FutureInstances
import scalaz.{ -\/, EitherT, \/, \/- }

sealed trait Result[A] {

  def map[B](f: A => B): Result[B]

  def flatMap[B](f: A => Result[B]): Result[B]

}

object Result {

  def apply[A](value: DomainError \/ A)(implicit builder: ResultBuilder): Result[A] =
    builder.build(value)

  def apply[A](value: A)(implicit builder: ResultBuilder): Result[A] = builder.build(value)

}


case class SyncResult[A](
  value: DomainError \/ A
) extends Result[A] {

  override def map[B](f: A => B): Result[B] = SyncResult(value.map(f))

  override def flatMap[B](f: A => Result[B]): Result[B] = {
    SyncResult(
      value.flatMap(f(_) match {
        case sync: SyncResult[B] => sync.value
        case async: AsyncResult[B] => Await.result(async.value.run, Duration.Inf)
      })
    )
  }
}

object SyncResult extends ToEitherOps {

  def apply[A](value: A): SyncResult[A] = {
    SyncResult(\/-(value))
  }

  def error[A](error: DomainError): SyncResult[A] = {
    SyncResult(-\/(error))
  }

}


case class AsyncResult[A](
  value: EitherT[Future, DomainError, A]
)(
  implicit ec: ExecutionContext
) extends FutureInstances with Result[A] with ToFutureOps with ToEitherOps {

  override def map[B](f: A => B): Result[B] = AsyncResult(value.map(f))

  override def flatMap[B](f: A => Result[B]): Result[B] = {
    AsyncResult(
      value.flatMap(f(_) match {
        case async: AsyncResult[B] => async.value
        case sync: SyncResult[B] => sync.value.future.et
      })
    )
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
