package usermanager.infrastructure.cache.shade.transaction

import usermanager.domain.transaction.async.AsyncTransaction
import usermanager.domain.transaction.sync.SyncTransactionRunner

class ShadeTransactionRunner extends SyncTransactionRunner {

  override def exec[A](transaction: AsyncTransaction[A]): A = transaction.asInstanceOf[ShadeTransaction].value

}
