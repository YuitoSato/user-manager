package usermanager.infrastructure.cache.shade.transaction

import usermanager.domain.transaction.async.AsyncTransaction

case class ShadeTransaction[+A](
  value: A
) extends AsyncTransaction[A] {

  override def map[B](f: A => B): AsyncTransaction[B] = ShadeTransaction(f(value))

  override def flatMap[B](f: A => AsyncTransaction[B]): AsyncTransaction[B] = f(ShadeTransaction(value))

}
