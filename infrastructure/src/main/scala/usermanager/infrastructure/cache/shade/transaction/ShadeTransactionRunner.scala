package usermanager.infrastructure.cache.shade.transaction

import javax.inject.Inject

import usermanager.domain.result.{ AsyncResult, Result }
import usermanager.domain.transaction.{ Transaction, TransactionRunner }

import scala.concurrent.ExecutionContext

class ShadeTransactionRunner @Inject()(implicit ec: ExecutionContext) extends TransactionRunner {

  override def execute[A](transaction: Transaction[A]): Result[A]  = AsyncResult(transaction.asInstanceOf[ShadeTransaction[A]].execute())

}
