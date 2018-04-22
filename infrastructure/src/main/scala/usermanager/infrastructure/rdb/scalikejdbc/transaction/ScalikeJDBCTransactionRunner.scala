package usermanager.infrastructure.rdb.scalikejdbc.transaction

import javax.inject.Inject

import scalikejdbc.DB
import usermanager.domain.result.{ AsyncResult, Result }
import usermanager.domain.syntax.{ ToEitherOps, ToFutureOps }
import usermanager.domain.transaction.{ Transaction, TransactionRunner }

import scala.concurrent.ExecutionContext

class ScalikeJDBCTransactionRunner @Inject()(
  implicit val ec: ExecutionContext
) extends TransactionRunner with ToEitherOps with ToFutureOps {

  override def run[A](transaction: Transaction[A]): Result[A] = AsyncResult {
    (DB localTx { session =>
      transaction.asInstanceOf[ScalikeJDBCTransaction[A]].execute(session)
    }).future.et
  }
}
