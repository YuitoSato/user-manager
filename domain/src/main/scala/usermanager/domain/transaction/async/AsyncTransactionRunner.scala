package usermanager.domain.transaction.async

import usermanager.domain.transaction.TransactionRunner

import scala.concurrent.Future

trait AsyncTransactionRunner extends TransactionRunner {

  def exec[A](transaction: AsyncTransaction[A]): Future[A]

}
