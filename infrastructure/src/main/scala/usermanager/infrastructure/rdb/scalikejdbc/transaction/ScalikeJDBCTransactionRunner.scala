package usermanager.infrastructure.rdb.scalikejdbc.transaction

import javax.inject.Inject

import scalikejdbc.DB
import usermanager.domain.result.sync.SyncResult
import usermanager.domain.transaction.sync.{ SyncTransaction, SyncTransactionRunner }

class ScalikeJDBCTransactionRunner @Inject() extends SyncTransactionRunner {

  override def exec[A](transaction: SyncTransaction[A]): SyncResult[A] = SyncResult {
    DB localTx { implicit session =>
      transaction.asInstanceOf[ScalikeJDBCTransaction[A]].value(session)
    }
  }
}
