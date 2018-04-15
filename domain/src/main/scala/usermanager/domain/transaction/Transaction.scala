package usermanager.domain.transaction

import usermanager.domain.result.Result

trait Transaction[A] { self =>

  def map[B](f: A => B): Transaction[B]

  def flatMap[B](f: A => Transaction[B]): Transaction[B]

  def foreach(f: A => Unit): Unit

  def run(implicit runner: TransactionRunner): Result[A] = runner.execute(self)

}
