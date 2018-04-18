package usermanager.domain.transaction

import usermanager.domain.result.MockResult

class MockTransactionRunner extends TransactionRunner {

  override def execute[A](transaction: Transaction[A]) = MockResult(transaction.asInstanceOf[MockTransaction[A]].value)

}
