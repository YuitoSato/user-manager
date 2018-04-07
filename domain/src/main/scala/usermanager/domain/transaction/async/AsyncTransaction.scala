package usermanager.domain.transaction.async

import usermanager.domain.result.async.AsyncResult
import usermanager.domain.transaction.Transaction

trait AsyncTransaction[A] extends Transaction[A] { self =>

  def map[B](f: A => B): AsyncTransaction[B]

  def flatMap[B](f: A => AsyncTransaction[B]): AsyncTransaction[B]

  def run(implicit runner: AsyncTransactionRunner): AsyncResult[A] = runner.exec(self)

}
