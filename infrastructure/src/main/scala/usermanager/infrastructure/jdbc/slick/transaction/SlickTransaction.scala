package usermanager.infrastructure.jdbc.slick.transaction

import slick.dbio.DBIO
import usermanager.domain.transaction.async.AsyncTransaction

import scala.concurrent.ExecutionContext

case class SlickTransaction[+A](
  value: DBIO[A]
)(
  implicit ec: ExecutionContext
) extends AsyncTransaction[A] {

  override def map[B](f: A => B): SlickTransaction[B] = SlickTransaction(value.map(f))

  override def flatMap[B](f: A => AsyncTransaction[B]): SlickTransaction[B] = f(value).asInstanceOf[SlickTransaction[B]]
}
