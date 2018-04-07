package usermanager.infrastructure.jdbc.slick.transaction

import slick.dbio.DBIO
import usermanager.domain.error.DomainError
import usermanager.domain.syntax.ToEitherOps
import usermanager.domain.transaction.async.AsyncTransaction
import usermanager.infrastructure.jdbc.slick.DBIOInstances

import scala.concurrent.ExecutionContext
import scalaz.EitherT

case class SlickTransaction[A](
  value: EitherT[DBIO, DomainError, A]
)(
  implicit val ec: ExecutionContext
) extends AsyncTransaction[A] with ToEitherOps with DBIOInstances {

  override def map[B](f: A => B): SlickTransaction[B] = {
    SlickTransaction(value.map(f))
  }

  override def flatMap[B](f: A => AsyncTransaction[B]): SlickTransaction[B] = {
    SlickTransaction(value.flatMap(f(_).asInstanceOf[SlickTransaction[B]].value))
  }

}
