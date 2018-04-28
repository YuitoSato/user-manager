package usermanager.domain.transaction

import usermanager.domain.result.SyncResult

class MockTransactionRunner extends TransactionRunner {

  override def run[A](transaction: Transaction[A]) = SyncResult(transaction.asInstanceOf[MockTransaction[A]].execute())

}
