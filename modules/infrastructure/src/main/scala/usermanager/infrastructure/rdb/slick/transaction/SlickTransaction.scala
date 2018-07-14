package usermanager.infrastructure.rdb.slick.transaction

import play.api.db.slick.{ DatabaseConfigProvider, HasDatabaseConfigProvider }
import scalaz.{ -\/, EitherT, \/, \/- }
import slick.dbio.DBIO
import slick.jdbc.JdbcProfile
import slick.jdbc.MySQLProfile.api._
import usermanager.domain.syntax.ToEitherOps
import usermanager.infrastructure.rdb.slick.DBIOInstances
import usermanager.lib.error
import usermanager.lib.error.Error
import usermanager.lib.result.{ AsyncResult, Result }
import usermanager.lib.transaction.Transaction

import scala.concurrent.ExecutionContext
import scala.util.{ Failure, Success, Try }

case class SlickTransaction[A](
  execute: () => EitherT[DBIO, error.Error, A]
)(
  implicit val ec: ExecutionContext,
  implicit val dbConfigProvider: DatabaseConfigProvider
) extends Transaction[A] with ToEitherOps with DBIOInstances with HasDatabaseConfigProvider[JdbcProfile] { self =>

  override def map[B](f: A => B): SlickTransaction[B] = {
    val exec = () => execute().map(f)
    SlickTransaction(exec)
  }

  override def flatMap[B](f: A => Transaction[B]): SlickTransaction[B] = {
    val exec = () => execute().map(f).flatMap(_.asInstanceOf[SlickTransaction[B]].execute())
    SlickTransaction(exec)
  }

  override def foreach(f: A => Unit): Unit = map(f)

  override def run: Result[A] = AsyncResult {
    val dbio = self.asInstanceOf[SlickTransaction[A]].execute().run
    db.run(dbio.transactionally).et
  }

  override def leftMap(f: error.Error => error.Error): Transaction[A] = SlickTransaction(() => execute().leftMap(f))

}

object SlickTransaction extends ToEitherOps {

  def from[A](execute: () => DBIO[A])(implicit ec: ExecutionContext, dbConfigProvider: DatabaseConfigProvider): SlickTransaction[A] = {
    val exec = () => {
      val dbio: DBIO[error.Error \/ A] = Try {
        execute().transactionally
      } match {
        case Success(r) => r.map(\/-(_))
        case Failure(l) => DBIO.successful(-\/(Error.Unexpected(l)))
      }
      dbio.et
    }
    SlickTransaction(exec)
  }

}
