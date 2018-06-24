package usermanager.infrastructure.rdb.scalikejdbc.transaction

import scalikejdbc.{ DB, DBSession }
import usermanager.domain.error.DomainError
import usermanager.domain.result.{ Result, SyncResult }
import usermanager.domain.syntax.{ ToEitherOps, ToFutureOps }
import usermanager.domain.transaction.Transaction

import scala.util.{ Failure, Success, Try }
import scalaz.{ -\/, \/, \/- }

case class ScalikeJDBCTransaction[A](
  execute: DBSession => DomainError \/ A
) extends Transaction[A] with ToEitherOps with ToFutureOps { self =>

  override def map[B](f: A => B): ScalikeJDBCTransaction[B] = {
    val exec = (session: DBSession) => execute(session).map(f)
    ScalikeJDBCTransaction(exec)
  }

  override def flatMap[B](f: A => Transaction[B]): Transaction[B] = {
    val exec = (session: DBSession) => execute(session).map(f).flatMap(_.asInstanceOf[ScalikeJDBCTransaction[B]].execute(session))
    ScalikeJDBCTransaction(exec)
  }

  override def foreach(f: A => Unit): Unit = map(f)

  override def run: Result[A] = SyncResult {
    DB localTx { session =>
      self.asInstanceOf[ScalikeJDBCTransaction[A]].execute(session)
    }
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
