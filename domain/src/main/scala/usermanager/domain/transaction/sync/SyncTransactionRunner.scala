package usermanager.domain.transaction.sync

import usermanager.domain.transaction.TransactionRunner

trait SyncTransactionRunner extends TransactionRunner {

  def exec[A](transaction: SyncTransaction[A]): A

}
