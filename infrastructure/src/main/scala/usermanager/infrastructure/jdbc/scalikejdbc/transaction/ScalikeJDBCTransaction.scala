package usermanager.infrastructure.jdbc.scalikejdbc.transaction

import scalikejdbc.DBSession
import usermanager.domain.transaction.Transaction

import scala.concurrent.{ ExecutionContext, Future }

case class ScalikeJDBCTransaction[A](
  value: Future[A]
)(
  implicit val ec: ExecutionContext,
  implicit val dBSession: DBSession
) extends Transaction[A] {

  override def map[B](f: A => B): Transaction[B] = ScalikeJDBCTransaction(value.map(f))

  override def flatMap[B](f: A => Transaction[B]): Transaction[B] =
    ScalikeJDBCTransaction(value.flatMap(f(_).asInstanceOf[ScalikeJDBCTransaction[B]].value))

}
