package usermanager.infrastructure.rdb.scalikejdbc.transaction

import javax.inject.Inject
import scalaz.{ \/, \/- }
import scalikejdbc.DBSession
import usermanager.lib.error.Error
import usermanager.lib.transaction.{ Transaction, TransactionBuilder }

class ScalikeJDBCTransactionBuilder @Inject() extends TransactionBuilder {

  override def build[A](value: \/[Error, A]): Transaction[A] = {
    def exec(dbSession: DBSession) = value
    ScalikeJDBCTransaction(exec)
  }

  override def build[A](value: A): Transaction[A] = {
    def exec(dbSession: DBSession) = \/-(value)
    ScalikeJDBCTransaction(exec)
  }

}
