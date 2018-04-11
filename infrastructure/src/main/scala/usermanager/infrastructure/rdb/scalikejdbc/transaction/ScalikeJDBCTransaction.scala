package usermanager.infrastructure.rdb.scalikejdbc.transaction

import scalikejdbc.DBSession
import usermanager.domain.error.DomainError
import usermanager.domain.transaction.sync.SyncTransaction

import scalaz.{ \/, \/- }

case class ScalikeJDBCTransaction[A](
  execute: DBSession => DomainError \/ A
) extends SyncTransaction[A] {

  override def map[B](f: A => B): ScalikeJDBCTransaction[B] = {
    def exec(session: DBSession) = execute(session).map(f)
    ScalikeJDBCTransaction(exec)
  }

  override def flatMap[B](f: A => SyncTransaction[B]): SyncTransaction[B] = {
    def exec(session: DBSession) = execute(session).map(f).flatMap(_.asInstanceOf[ScalikeJDBCTransaction[B]].execute(session))
    ScalikeJDBCTransaction(exec)
  }

}
