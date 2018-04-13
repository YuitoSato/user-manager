package usermanager.domain.transaction

import usermanager.domain.result.Result

trait TransactionRunner {

  def execute[A](transaction: Transaction[A]): Result[A]

}
