package usermanager.infrastructure.jdbc.scalikejdbc.transaction

import scalikejdbc.DBSession
import usermanager.domain.transaction.async.AsyncTransaction

import scala.concurrent.{ ExecutionContext, Future }

case class ScalikeJDBCTransaction[A](
  value: Future[A]
)(
  implicit val ec: ExecutionContext,
  implicit val dBSession: DBSession
) extends AsyncTransaction[A] {

  override def map[B](f: A => B): AsyncTransaction[B] = ScalikeJDBCTransaction(value.map(f))

  override def flatMap[B](f: A => AsyncTransaction[B]): AsyncTransaction[B] =
    ScalikeJDBCTransaction(value.flatMap(f(_).asInstanceOf[ScalikeJDBCTransaction[B]].value))

}
