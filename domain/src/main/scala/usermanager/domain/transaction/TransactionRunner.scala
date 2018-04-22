package usermanager.domain.transaction

import usermanager.domain.result.Result

trait TransactionRunner {

  def run[A](transaction: Transaction[A]): Result[A]

}
