package usermanager.infrastructure.jdbc.slick.transaction

import repositories.transaction.Transaction
import slick.dbio.DBIO

import scala.concurrent.ExecutionContext

case class SlickTransaction[+A](
  value: DBIO[A]
)(
  implicit ec: ExecutionContext
) extends Transaction[A] {

  override def map[B](f: A => B): SlickTransaction[B] = SlickTransaction(value.map(f))

  /**
    * valueをflatMap。DBIOのflatMapは引数にA => DBIO[B]をとる
    * f(_)で A => Transaction[B]
    * asInstanceOf[SlickTransaction[B]]で Transaction[B] => SlickTransaction[B]
    * 最後にvalueで SlickTransaction[B] => DBIO[B]
    * よって A => DBIO[B] という型が完成する。
    */
  override def flatMap[B](f: A => Transaction[B]): SlickTransaction[B] = {
    SlickTransaction(value.flatMap(f(_).asInstanceOf[SlickTransaction[B]].value))
  }
}
