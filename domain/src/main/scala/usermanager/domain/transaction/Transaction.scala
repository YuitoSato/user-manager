package usermanager.domain.transaction

import usermanager.domain.result.Result

trait Transaction[A, C] {

  val execute: C

  def map[B](f: A => B): Transaction[B, C]

  def flatMap[B](f: A => Transaction[B, C]): Transaction[B, C]

  def foreach(f: A => Unit): Unit

  def run: Result[A]

}
