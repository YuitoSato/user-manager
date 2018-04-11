package usermanager.infrastructure.rdb.scalikejdbc.transaction

import scalikejdbc.DBSession
import usermanager.domain.error.DomainError
import usermanager.domain.transaction.sync.SyncTransaction

import scalaz.{ \/, \/- }

case class ScalikeJDBCTransaction[A](
  value: DBSession => DomainError \/ A
) extends SyncTransaction[A] {

  override def map[B](f: A => B): ScalikeJDBCTransaction[B] = {
    def v(session: DBSession) = value(session).map(f)
    ScalikeJDBCTransaction(v)
  }

  override def flatMap[B](f: A => SyncTransaction[B]): SyncTransaction[B] = {
    def v(session: DBSession) = value(session).map(f).flatMap(_.asInstanceOf[ScalikeJDBCTransaction[B]].value(session))
    ScalikeJDBCTransaction(v)
  }

}
