package usermanager.domain.transaction

import usermanager.domain.error.DomainError

import scalaz.{ \/, \/- }

case class MockTransaction[A](
  execute: () => DomainError \/ A
) extends Transaction[A] {

  override def map[B](f: A => B): Transaction[B] = {
    val exec = () => execute().map(f)
    MockTransaction(exec)
  }

  override def flatMap[B](f: A => Transaction[B]): Transaction[B] = {
    val exec = () => execute().map(f).flatMap(_.asInstanceOf[MockTransaction[B]].execute())
    MockTransaction(exec)
  }

  override def foreach(f: A => Unit): Unit = map(f)

}

object MockTransaction {

  def from[A](execute: () => A): MockTransaction[A] = {
    val exec = () => \/-(execute())
    MockTransaction(exec)
  }
}
