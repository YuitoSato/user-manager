package usermanager.infrastructure.rdb.slick.transaction

import play.api.db.slick.{ DatabaseConfigProvider, HasDatabaseConfigProvider }
import slick.dbio.DBIO
import slick.jdbc.JdbcProfile
import slick.jdbc.MySQLProfile.api._
import usermanager.domain.error.DomainError
import usermanager.domain.result.{ AsyncResult, Result }
import usermanager.domain.syntax.ToEitherOps
import usermanager.domain.transaction.Transaction
import usermanager.infrastructure.rdb.slick.DBIOInstances

import scala.concurrent.ExecutionContext
import scala.util.{ Failure, Success, Try }
import scalaz.{ -\/, EitherT, \/, \/- }

case class SlickTransaction[A](
  execute: () => EitherT[DBIO, DomainError, A]
)(
  implicit val ec: ExecutionContext,
  implicit val dbConfigProvider: DatabaseConfigProvider
) extends Transaction[A, () => EitherT[DBIO, DomainError, _]] with ToEitherOps with DBIOInstances with HasDatabaseConfigProvider[JdbcProfile] { self =>

  override def map[B](f: A => B): SlickTransaction[B] = {
    val exec = () => execute().map(f)
    SlickTransaction(exec)
  }

  override def flatMap[B](f: A => Transaction[B, () => EitherT[DBIO, DomainError, _]]): SlickTransaction[B] = {
    val exec = () => execute().map(f).flatMap(_.asInstanceOf[SlickTransaction[B]].execute())
    SlickTransaction(exec)
  }

  override def foreach(f: A => Unit): Unit = map(f)

  override def run: Result[A] = AsyncResult {
    val dbio = self.asInstanceOf[SlickTransaction[A]].execute().run
    db.run(dbio.transactionally).et
  }

}

object SlickTransaction extends ToEitherOps {

  def from[A](execute: () => DBIO[A])(implicit ec: ExecutionContext, dbConfigProvider: DatabaseConfigProvider): SlickTransaction[A] = {
    val exec = () => {
      val dbio: DBIO[DomainError \/ A] = Try {
        execute().transactionally
      } match {
        case Success(r) => r.map(\/-(_))
        case Failure(l) => DBIO.successful(-\/(DomainError.Unexpected(l)))
      }
      dbio.et
    }
    SlickTransaction(exec)
  }

}
