package usermanager.domain.transaction

import usermanager.domain.error.DomainError

import scalaz.\/

case class MockTransaction[A](
  value: DomainError \/ A
) extends Transaction[A] {

  override def map[B](f: A => B): Transaction[B] = MockTransaction(value.map(f))

  override def flatMap[B](f: A => Transaction[B]): Transaction[B] =
    MockTransaction(value.flatMap(f(_).asInstanceOf[MockTransaction[B]].value))

  override def foreach(f: A => Unit): Unit = value.foreach(f)

}
