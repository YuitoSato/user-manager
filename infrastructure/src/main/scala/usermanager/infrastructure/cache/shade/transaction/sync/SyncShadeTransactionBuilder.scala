package usermanager.infrastructure.cache.shade.transaction.sync

import javax.inject.Inject

import usermanager.domain.error.DomainError
import usermanager.domain.syntax.ToEitherOps
import usermanager.domain.transaction.sync.{ SyncTransaction, SyncTransactionBuilder }

import scala.concurrent.ExecutionContext
import scalaz.{ \/, \/- }

class SyncShadeTransactionBuilder @Inject()(implicit ec: ExecutionContext) extends SyncTransactionBuilder with ToEitherOps {

  override def exec[A](value: \/[DomainError, A]): SyncTransaction[A] = SyncShadeTransaction(value)

  override def exec[A](value: A): SyncTransaction[A] = {
    SyncShadeTransaction(\/-(value))
  }

}
