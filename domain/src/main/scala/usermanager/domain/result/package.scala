package usermanager.domain

import usermanager.domain.error.DomainError
import usermanager.domain.result.async.AsyncTransactionResult
import usermanager.domain.result.sync.SyncTransactionResult
import usermanager.domain.transaction.Transaction
import usermanager.domain.transaction.async.AsyncTransaction
import usermanager.domain.transaction.sync.SyncTransaction

import scalaz.syntax.std.ToOptionOps

package object result {

  implicit class TransactionOptionOps[A](transaction: Transaction[Option[A]]) extends ToOptionOps {
    def ifNotExists(f: => DomainError)(): TransactionResult[A] = {
      transaction match {
        case async: AsyncTransaction[Option[A]] => AsyncTransactionResult(async.map(_ \/> f))
        case sync: AsyncTransaction[Option[A]] => SyncTransactionResult(sync.map(_ \/> f))
        case _ => throw new Exception("DI ERROR")
      }
    }
  }
}
