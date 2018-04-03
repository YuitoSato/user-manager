package usermanager.infrastructure.jdbc.scalikejdbc.transaction

import scalikejdbc.{ DB, DBSession }
import usermanager.domain.transaction.{ Transaction, TransactionRunner }

import scala.concurrent.Future

class ScalikeJDBCTaskRunner()(
  implicit dBSession: DBSession
) extends TransactionRunner {

  override def exec[A](transaction: Transaction[A]): Future[A] = {
    DB localTx { implicit session =>
      transaction.asInstanceOf[ScalikeJDBCTransaction[A]].value
    }
  }
}
