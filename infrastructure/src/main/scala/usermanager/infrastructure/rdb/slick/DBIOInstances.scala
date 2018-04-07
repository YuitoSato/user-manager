package usermanager.infrastructure.rdb.slick

import slick.dbio.DBIO

import scala.concurrent.ExecutionContext
import scalaz.Monad

trait DBIOInstances {

  implicit val ec: ExecutionContext
  implicit val monad: DBIOMonad = new DBIOMonad

  class DBIOMonad extends Monad[DBIO] {
    override def point[A](value: => A): DBIO[A] = DBIO.successful(value)
    override def bind[A, B](transaction: DBIO[A])(f: A => DBIO[B]): DBIO[B] = transaction.flatMap(f)
  }

}
