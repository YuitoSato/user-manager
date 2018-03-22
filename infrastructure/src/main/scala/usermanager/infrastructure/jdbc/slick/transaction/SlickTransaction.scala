package usermanager.infrastructure.jdbc.slick.transaction

import slick.dbio.DBIO
import usermanager.domain.transaction.Transaction

import scala.concurrent.ExecutionContext

case class SlickTransaction[+A](
  value: DBIO[A]
)(
  implicit ec: ExecutionContext
) extends Transaction[A] {

  override def map[B](f: A => B): SlickTransaction[B] = SlickTransaction(value.map(f))

  override def flatMap[B](f: A => Transaction[B]): SlickTransaction[B] = {
    SlickTransaction(value.flatMap(f(_).asInstanceOf[SlickTransaction[B]].value))
  }
}
