package usermanager.infrastructure.rdb.scalikejdbc.transaction

import scalikejdbc.{ DB, DBSession }
import usermanager.domain.result.{ Result, SyncResult }
import usermanager.domain.syntax.{ ToEitherOps, ToFutureOps }

import scala.util.{ Failure, Success, Try }
import scalaz.{ -\/, \/, \/- }
import usermanager.lib.error
import usermanager.lib.error.Error
import usermanager.lib.error.transaction.Transaction

case class ScalikeJDBCTransaction[A](
  execute: DBSession => error.Error \/ A
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

  override def leftMap(f: error.Error => error.Error): Transaction[A] = ScalikeJDBCTransaction((session: DBSession)=> execute(session).leftMap(f))
  
}

object ScalikeJDBCTransaction {

  def from[A](execute: DBSession => A): ScalikeJDBCTransaction[A] = {
    val exec = (session: DBSession) => {
      Try {
        execute(session)
      } match {
        case Success(r) => \/-(r)
        case Failure(l) => -\/(Error.Unexpected(l))
      }
    }
    ScalikeJDBCTransaction(exec)
  }

}
