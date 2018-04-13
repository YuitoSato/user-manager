package usermanager.infrastructure.rdb.scalikejdbc.transaction

import javax.inject.Inject

import scalikejdbc.DB
import usermanager.domain.result.Result
import usermanager.domain.syntax.{ ToEitherOps, ToFutureOps }
import usermanager.domain.transaction.{ Transaction, TransactionRunner }

import scala.concurrent.ExecutionContext

class ScalikeJDBCTransactionRunner @Inject()(
  implicit val ec: ExecutionContext
) extends TransactionRunner with ToEitherOps with ToFutureOps {

  override def execute[A](transaction: Transaction[A]): Result[A] = {
    (DB localTx { session =>
      transaction.asInstanceOf[ScalikeJDBCTransaction[A]].execute(session)
    }).future.et
  }
}
