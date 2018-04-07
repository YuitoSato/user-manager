package usermanager.infrastructure.cache.shade.transaction.async

import usermanager.domain.result.async.AsyncResult
import usermanager.domain.transaction.async.{ AsyncTransaction, AsyncTransactionRunner }

import scala.concurrent.ExecutionContext

class AsyncShadeTransactionRunner()(implicit ec: ExecutionContext) extends AsyncTransactionRunner {

  override def exec[A](transaction: AsyncTransaction[A]): AsyncResult[A] = AsyncResult(transaction.asInstanceOf[AsyncShadeTransaction[A]].value)

}
