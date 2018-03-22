package usermanager.infrastructure.jdbc.scalikejdbc.transaction

import scalikejdbc.DBSession
import usermanager.domain.transaction.Transaction

import scala.concurrent.{ ExecutionContext, Future }

class ScalikejdbcTransaction[+A](
  value: DBSession => Future[A]
)(
  implicit ec: ExecutionContext
) extends Transaction[A] {

  override def map[B](f: A => B): Transaction[B] = ???

  override def flatMap[B](f: A => Transaction[B]): Transaction[B] = ???

}
