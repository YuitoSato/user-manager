package usermanager.infrastructure.rdb.slick.transaction

import slick.dbio.DBIO
import slick.jdbc.MySQLProfile.api._
import usermanager.domain.error.DomainError
import usermanager.domain.syntax.ToEitherOps
import usermanager.domain.transaction.Transaction
import usermanager.infrastructure.rdb.slick.DBIOInstances

import scala.concurrent.ExecutionContext
import scala.util.{ Failure, Success, Try }
import scalaz.{ -\/, EitherT, \/, \/- }

case class SlickTransaction[A](
  execute: () => EitherT[DBIO, DomainError, A]
)(
  implicit val ec: ExecutionContext
) extends Transaction[A] with ToEitherOps with DBIOInstances {

  override def map[B](f: A => B): SlickTransaction[B] = {
    val exec = () => execute().map(f)
    SlickTransaction(exec)
  }

  override def flatMap[B](f: A => Transaction[B]): SlickTransaction[B] = {
    val exec = () => execute().map(f).flatMap(_.asInstanceOf[SlickTransaction[B]].execute())
    SlickTransaction(exec)
  }

  override def foreach(f: A => Unit): Unit = map(f)

}

object SlickTransaction extends ToEitherOps {

  def from[A](execute: () => DBIO[A])(implicit ec: ExecutionContext): SlickTransaction[A] = {
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
