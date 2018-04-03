package usermanager.infrastructure.jdbc.scalikejdbc.transaction

import scalikejdbc.{ DB, DBSession }
import usermanager.domain.transaction.TransactionRunner
import usermanager.domain.transaction.async.{ AsyncTransaction, AsyncTransactionRunner }

import scala.concurrent.Future

class ScalikeJDBCTransactionRunner()(
  implicit dBSession: DBSession
) extends TransactionRunner {

  override def exec[A](transaction: AsyncTransaction[A]): Future[A] = {
    DB localTx { implicit session =>
      transaction.asInstanceOf[ScalikeJDBCTransaction[A]].value
    }
  }
}
