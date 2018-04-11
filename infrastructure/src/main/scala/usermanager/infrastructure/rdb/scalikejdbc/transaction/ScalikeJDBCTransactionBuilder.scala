package usermanager.infrastructure.rdb.scalikejdbc.transaction

import javax.inject.Inject

import scalikejdbc.DBSession
import usermanager.domain.error.DomainError
import usermanager.domain.transaction.sync.{ SyncTransaction, SyncTransactionBuilder }

import scalaz.{ \/, \/- }

class ScalikeJDBCTransactionBuilder @Inject() extends SyncTransactionBuilder {

  override def execute[A](value: \/[DomainError, A]): SyncTransaction[A] = {
    def exec(dbSession: DBSession) = value
    ScalikeJDBCTransaction(exec)
  }

  override def execute[A](value: A): SyncTransaction[A] = {
    def exec(dbSession: DBSession) = \/-(value)
    ScalikeJDBCTransaction(exec)
  }

}
