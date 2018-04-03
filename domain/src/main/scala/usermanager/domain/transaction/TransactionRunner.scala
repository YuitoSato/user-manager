package usermanager.domain.transaction

import scala.concurrent.Future

trait TransactionRunner {

  def exec[A](transaction: Transaction[A]): Future[A]

}
