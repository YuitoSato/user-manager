package usermanager.infrastructure.jdbc.scalikejdbc.transaction

import scalikejdbc.DBSession
import usermanager.domain.transaction.async.AsyncTransaction

import scala.concurrent.ExecutionContext

case class ScalikeJDBCTransaction[A](
  value: DBSession => A
)(
  implicit val ec: ExecutionContext,
  implicit val dbSession: DBSession
) extends AsyncTransaction[A] {

  override def map[B](f: A => B): ScalikeJDBCTransaction[B] = {
    def run(session: DBSession) = f(value(session))
    ScalikeJDBCTransaction(run)
  }

  override def flatMap[B](f: A => AsyncTransaction[B]): ScalikeJDBCTransaction[B] = {
    f(value).asInstanceOf[ScalikeJDBCTransaction[B]]
  }
}
