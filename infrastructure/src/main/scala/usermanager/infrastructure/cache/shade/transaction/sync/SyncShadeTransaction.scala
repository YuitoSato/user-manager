package usermanager.infrastructure.cache.shade.transaction.sync

import usermanager.domain.error.DomainError
import usermanager.domain.transaction.sync.SyncTransaction

import scala.concurrent.ExecutionContext
import scalaz.\/
import scalaz.std.FutureInstances

case class SyncShadeTransaction[A](
  value: DomainError \/ A
)(
  implicit ec: ExecutionContext
) extends SyncTransaction[A] with FutureInstances {

  override def map[B](f: A => B): SyncTransaction[B] = SyncShadeTransaction(value.map(f))

  override def flatMap[B](f: A => SyncTransaction[B]): SyncTransaction[B] = SyncShadeTransaction(value.flatMap(f(_).asInstanceOf[SyncShadeTransaction[B]].value))

}
