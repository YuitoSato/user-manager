package usermanager.infrastructure.rdb.scalikejdbc.transaction

import scalikejdbc.DBSession
import usermanager.domain.error.DomainError
import usermanager.domain.transaction.Transaction

import scala.util.{ Failure, Success, Try }
import scalaz.{ -\/, \/, \/- }

case class ScalikeJDBCTransaction[A](
  execute: DBSession => DomainError \/ A
) extends Transaction[A] {

  override def map[B](f: A => B): ScalikeJDBCTransaction[B] = {
    val exec = (session: DBSession) => execute(session).map(f)
    ScalikeJDBCTransaction(exec)
  }

  override def flatMap[B](f: A => Transaction[B]): Transaction[B] = {
    val exec = (session: DBSession) => execute(session).map(f).flatMap(_.asInstanceOf[ScalikeJDBCTransaction[B]].execute(session))
    ScalikeJDBCTransaction(exec)
  }

}

object ScalikeJDBCTransaction {

  def from[A](execute: DBSession => A): ScalikeJDBCTransaction[A] = {
    val exec = (session: DBSession) => {
      Try {
        execute(session)
      } match {
        case Success(r) => \/-(r)
        case Failure(l) => -\/(DomainError.Unexpected(l))
      }
    }
    ScalikeJDBCTransaction(exec)
  }

}
