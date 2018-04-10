package usermanager.infrastructure.rdb.scalikejdbc.transaction

import javax.inject.Inject

import scalikejdbc.DBSession
import usermanager.domain.error.DomainError
import usermanager.domain.transaction.sync.{ SyncTransaction, SyncTransactionBuilder }

import scalaz.{ \/, \/- }

class ScalikeJDBCTransactionBuilder @Inject() extends SyncTransactionBuilder {

  override def exec[A](value: \/[DomainError, A]): SyncTransaction[A] = {
    def v(dbSession: DBSession) = value
    ScalikeJDBCTransaction(v)
  }

  override def exec[A](value: A): SyncTransaction[A] = {
    def v(dbSession: DBSession) = \/-(value)
    ScalikeJDBCTransaction(v)
  }

}
