package usermanager.lib.transaction

import scalaz.-\/
import usermanager.lib.error.Error
import usermanager.lib.result.Result

trait Transaction[A] { self =>

  def map[B](f: A => B): Transaction[B]

  def flatMap[B](f: A => Transaction[B]): Transaction[B]

  def leftMap(f: Error => Error): Transaction[A]

  def foreach(f: A => Unit): Unit = map(f)

  def zipWith[U, R](that: Transaction[U])(f: (A, U) => R): Transaction[R] = flatMap(a => that.map(u => f(a, u)))

  def run: Result[A]

  def assert(p: A => Boolean, error: => Error)(implicit builder: TransactionBuilder): Transaction[A] = {
    flatMap(a =>
      if (p(a))
        self
      else
        builder.build(-\/(error))
    )
  }

  def assertWithA(p: A => Boolean, error: A => Error)(implicit builder: TransactionBuilder): Transaction[A] = {
    flatMap(a =>
      if (p(a))
        self
      else
        builder.build(-\/(error(a)))
    )
  }

}
