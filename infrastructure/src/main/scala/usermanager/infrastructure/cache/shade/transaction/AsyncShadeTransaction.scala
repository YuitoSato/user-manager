package usermanager.infrastructure.cache.shade.transaction

import usermanager.domain.error.DomainError
import usermanager.domain.transaction.Transaction

import scala.concurrent.{ ExecutionContext, Future }
import scalaz.EitherT
import scalaz.std.FutureInstances

case class AsyncShadeTransaction[A](
  value: EitherT[Future, DomainError, A]
)(
  implicit ec: ExecutionContext
) extends Transaction[A] with FutureInstances {

  override def map[B](f: A => B): Transaction[B] = AsyncShadeTransaction(value.map(f))

  override def flatMap[B](f: A => Transaction[B]): Transaction[B] = AsyncShadeTransaction(value.flatMap(f(_).asInstanceOf[AsyncShadeTransaction[B]].value))

}
