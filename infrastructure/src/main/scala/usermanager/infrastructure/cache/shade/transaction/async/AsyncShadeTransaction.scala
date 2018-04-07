package usermanager.infrastructure.cache.shade.transaction.async

import usermanager.domain.error.DomainError
import usermanager.domain.transaction.async.AsyncTransaction

import scala.concurrent.{ ExecutionContext, Future }
import scalaz.EitherT
import scalaz.std.FutureInstances

case class AsyncShadeTransaction[A](
  value: EitherT[Future, DomainError, A]
)(
  implicit ec: ExecutionContext
) extends AsyncTransaction[A] with FutureInstances {

  override def map[B](f: A => B): AsyncTransaction[B] = AsyncShadeTransaction(value.map(f))

  override def flatMap[B](f: A => AsyncTransaction[B]): AsyncTransaction[B] = AsyncShadeTransaction(value.flatMap(f(_).asInstanceOf[AsyncShadeTransaction[B]].value))

}
