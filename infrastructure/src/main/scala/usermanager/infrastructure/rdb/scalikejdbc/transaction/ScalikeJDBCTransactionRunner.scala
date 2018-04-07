package usermanager.infrastructure.rdb.scalikejdbc.transaction

import scalikejdbc.{ DB, DBSession }
import usermanager.domain.result.sync.SyncResult
import usermanager.domain.transaction.sync.{ SyncTransaction, SyncTransactionRunner }

class ScalikeJDBCTransactionRunner()(
  implicit dBSession: DBSession
) extends SyncTransactionRunner {

  override def exec[A](transaction: SyncTransaction[A]): SyncResult[A] = SyncResult {
    DB localTx { implicit session =>
      transaction.asInstanceOf[ScalikeJDBCTransaction[A]].value(session)
    }
  }
}
