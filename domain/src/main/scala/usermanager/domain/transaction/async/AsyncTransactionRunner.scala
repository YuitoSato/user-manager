package usermanager.domain.transaction.async

import usermanager.domain.result.async.AsyncResult
import usermanager.domain.transaction.TransactionRunner

trait AsyncTransactionRunner extends TransactionRunner {

  def execute[A](transaction: AsyncTransaction[A]): AsyncResult[A]

}
