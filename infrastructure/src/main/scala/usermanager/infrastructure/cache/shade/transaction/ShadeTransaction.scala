package usermanager.infrastructure.cache.shade.transaction

import usermanager.domain.transaction.Transaction

import scala.util.Try

case class ShadeTransaction[+A](
  value: Try[A]
) extends Transaction[A] {

  override def map[B](f: A => B): Transaction[B] = ???

  override def flatMap[B](f: A => Transaction[B]): Transaction[B] = ???
}
