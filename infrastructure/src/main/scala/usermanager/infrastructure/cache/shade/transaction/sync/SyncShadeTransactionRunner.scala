package usermanager.infrastructure.cache.shade.transaction.sync

import javax.inject.Inject

import usermanager.domain.result.sync.SyncResult
import usermanager.domain.transaction.sync.{ SyncTransaction, SyncTransactionRunner }

import scala.concurrent.ExecutionContext

class SyncShadeTransactionRunner @Inject()(implicit ec: ExecutionContext) extends SyncTransactionRunner {

  override def execute[A](transaction: SyncTransaction[A]): SyncResult[A] = SyncResult(transaction.asInstanceOf[SyncShadeTransaction[A]].value)

}
