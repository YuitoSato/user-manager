package usermanager.infrastructure.rdb.slick.transaction

import slick.dbio.DBIO
import usermanager.domain.error.DomainError
import usermanager.domain.syntax.ToEitherOps
import usermanager.domain.transaction.Transaction
import usermanager.infrastructure.rdb.slick.DBIOInstances

import scala.concurrent.ExecutionContext
import scalaz.EitherT

case class SlickTransaction[A](
  value: EitherT[DBIO, DomainError, A]
)(
  implicit val ec: ExecutionContext
) extends Transaction[A] with ToEitherOps with DBIOInstances {

  override def map[B](f: A => B): SlickTransaction[B] = {
    SlickTransaction(value.map(f))
  }

  override def flatMap[B](f: A => Transaction[B]): SlickTransaction[B] = {
    SlickTransaction(value.flatMap(f(_).asInstanceOf[SlickTransaction[B]].value))
  }

}
