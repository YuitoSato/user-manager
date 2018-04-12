package usermanager.infrastructure.cache.shade.transaction

import javax.inject.Inject

import usermanager.domain.result.AsyncResult
import usermanager.domain.transaction.{ Transaction, TransactionRunner }

import scala.concurrent.ExecutionContext

class AsyncShadeTransactionRunner @Inject()(implicit ec: ExecutionContext) extends TransactionRunner {

  override def execute[A](transaction: Transaction[A]): AsyncResult[A]  = AsyncResult(transaction.asInstanceOf[AsyncShadeTransaction[A]].value)

}
