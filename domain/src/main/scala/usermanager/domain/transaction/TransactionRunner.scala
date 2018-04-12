package usermanager.domain.transaction

import usermanager.domain.result.AsyncResult

trait TransactionRunner {

  def execute[A](transaction: Transaction[A]): AsyncResult[A]

}
