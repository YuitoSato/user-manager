package usermanager.infrastructure.rdb.scalikejdbc.transaction

import javax.inject.Inject

import scalikejdbc.DBSession
import usermanager.domain.error.DomainError
import usermanager.domain.transaction.{ Transaction, TransactionBuilder }

import scalaz.{ \/, \/- }

class ScalikeJDBCTransactionBuilder @Inject() extends TransactionBuilder {

  override def build[A](value: \/[DomainError, A]): Transaction[A] = {
    def exec(dbSession: DBSession) = value
    ScalikeJDBCTransaction(exec)
  }

  override def build[A](value: A): Transaction[A] = {
    def exec(dbSession: DBSession) = \/-(value)
    ScalikeJDBCTransaction(exec)
  }

}
