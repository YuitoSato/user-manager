package usermanager.domain.transaction.async

import usermanager.domain.result.async.{ AsyncResult, AsyncTransactionResult }
import usermanager.domain.transaction.TransactionRunner

trait AsyncTransactionRunner extends TransactionRunner {

  def exec[A](transactionResult: AsyncTransactionResult[A]): AsyncResult[A]

}
