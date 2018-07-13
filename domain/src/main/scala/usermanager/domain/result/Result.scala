package usermanager.domain.result

import scalaz.std.{ EitherInstances, FutureInstances }
import scalaz.{ -\/, EitherT, \/ }
import usermanager.domain.error.Error

import scala.concurrent.duration.Duration
import scala.concurrent.{ Await, ExecutionContext, Future }

trait Result[A] { self =>

  def map[B](f: A => B): Result[B]

  def flatMap[B](f: A => Result[B]): Result[B]

  def leftMap(f: Error => Error): Result[A]

  def foreach(f: A => Unit): Unit = map(f)

  def zipWith[U, R](that: Result[U])(f: (A, U) => R): Result[R] = flatMap(a => that.map(u => f(a, u)))

  def assert(p: A => Boolean, error: => Error)(implicit builder: ResultBuilder): Result[A] = {
    flatMap(a =>
      if (p(a))
        self
      else
        builder.build(-\/(error))
    )
  }

  def assertWithA(p: A => Boolean, error: A => Error)(implicit builder: ResultBuilder): Result[A] = {
    flatMap(a =>
      if (p(a))
        self
      else
        builder.build(-\/(error(a)))
    )
  }


}

case class SyncResult[A](
  value: Error \/ A
) extends Result[A] with EitherInstances { self =>

  override def map[B](f: A => B): Result[B] = SyncResult(value.map(f))

  override def flatMap[B](f: A => Result[B]): Result[B] = {
    SyncResult(
      value.flatMap(f(_) match {
        case sync: SyncResult[B] => sync.value
        case async: AsyncResult[B] => Await.result(async.value.run, Duration.Inf)
      })
    )
  }

  override def leftMap(f: Error => Error): Result[A] = SyncResult(value.leftMap(f))

}


case class AsyncResult[A](
  value: EitherT[Future, Error, A]
)(
  implicit ec: ExecutionContext
) extends Result[A] with FutureInstances with EitherInstances {

  override def map[B](f: A => B): Result[B] = AsyncResult(value.map(f))

  override def flatMap[B](f: A => Result[B]): Result[B] = {
    AsyncResult(
      value.flatMap(f(_) match {
        case async: AsyncResult[B] => async.value
        case sync: SyncResult[B] => EitherT(Future.successful(sync.value))
      })
    )
  }

  override def leftMap(f: Error => Error): Result[A] = AsyncResult(value.leftMap(f))

}
