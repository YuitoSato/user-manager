package usermanager.infrastructure.jdbc.scalikejdbc.transaction

import scalikejdbc.{ DB, DBSession }
import usermanager.domain.result.async.AsyncTransactionResult
import usermanager.domain.result.sync.{ SyncResult, SyncTransactionResult }
import usermanager.domain.transaction.TransactionRunner
import usermanager.domain.transaction.async.AsyncTransaction

class ScalikeJDBCTransactionRunner()(
  implicit dBSession: DBSession
) extends TransactionRunner {

  // TODO ここらへんはテコいれ。多分遅延評価にしないと動かない。
  override def exec[A](transactionResult: SyncTransactionResult[A]): SyncResult[A] = {
    transactionResult.value

    transactionResult.map(a =>)

    val future = DB localTx { implicit session =>
      val a = transactionResult.value


      transaction.asInstanceOf[ScalikeJDBCTransaction[A]].value
    }
    SyncResult(future)
  }
}
