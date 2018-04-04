package usermanager.domain.transaction.sync

import usermanager.domain.result.sync.{ SyncResult, SyncTransactionResult }
import usermanager.domain.transaction.TransactionRunner

trait SyncTransactionRunner extends TransactionRunner {

  def exec[A](transactionResult: SyncTransactionResult[A]): SyncResult[A]

}
