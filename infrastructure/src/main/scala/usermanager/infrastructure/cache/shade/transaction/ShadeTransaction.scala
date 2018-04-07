package usermanager.infrastructure.cache.shade.transaction

import usermanager.domain.error.DomainError
import usermanager.domain.transaction.async.AsyncTransaction

import scala.concurrent.{ ExecutionContext, Future }
import scalaz.EitherT
import scalaz.std.FutureInstances

case class ShadeTransaction[A](
  value: EitherT[Future, DomainError, A]
)(
  implicit ec: ExecutionContext
) extends AsyncTransaction[A] with FutureInstances {

  override def map[B](f: A => B): AsyncTransaction[B] = ShadeTransaction(value.map(f))

  override def flatMap[B](f: A => AsyncTransaction[B]): AsyncTransaction[B] = ShadeTransaction(value.flatMap(f(_).asInstanceOf[ShadeTransaction[B]].value))

}
