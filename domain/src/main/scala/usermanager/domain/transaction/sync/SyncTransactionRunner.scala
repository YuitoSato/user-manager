package usermanager.domain.transaction.sync

import usermanager.domain.result.sync.SyncResult
import usermanager.domain.transaction.TransactionRunner

trait SyncTransactionRunner extends TransactionRunner {

  def execute[A](transaction: SyncTransaction[A]): SyncResult[A]

}
