package usermanager.domain.result

import usermanager.domain.result.async.AsyncTransactionResult
import usermanager.domain.result.sync.SyncTransactionResult
import usermanager.domain.transaction.TransactionRunner
import usermanager.domain.transaction.async.AsyncTransactionRunner
import usermanager.domain.transaction.sync.SyncTransactionRunner

trait TransactionResult[+A] { self =>

  def map[B](f: A => B): TransactionResult[B]

  def flatMap[B](f: A => TransactionResult[B]): TransactionResult[B]

  // implicitをしないほうがいいかもしれない。今だと実行時でコンパイルエラーで弾けないのでやはり、メソッドはsyncとasyncで分けるべき
  def run(runner: TransactionRunner): Result[A] = (runner, self) match {
    case (syncR: SyncTransactionRunner, syncT: SyncTransactionResult[A]) => syncR.exec(syncT)
    case (asyncR: AsyncTransactionRunner, asyncT: AsyncTransactionResult[A]) => asyncR.exec(asyncT)
    case _ => throw new Exception("DI ERROR")
  }

}
