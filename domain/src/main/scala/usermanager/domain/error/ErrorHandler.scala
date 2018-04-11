package usermanager.domain.error

import usermanager.domain.transaction.async.{ AsyncTransaction, AsyncTransactionBuilder }
import usermanager.domain.transaction.sync.{ SyncTransaction, SyncTransactionBuilder }

import scalaz.syntax.std.ToOptionOps
import scalaz.{ -\/, \/- }

trait ErrorHandler extends ToOptionOps {

  implicit class AsyncTransactionOptionErrorHandler[A](transactionOpt: AsyncTransaction[Option[A]]) {
    def ifNotExists(f: => DomainError)(implicit builder: AsyncTransactionBuilder): AsyncTransaction[A] = {
      transactionOpt.flatMap(opt => builder.execute(opt \/> f))
    }
  }

  implicit class AsyncTransactionBooleanErrorHandler(transactionBool: AsyncTransaction[Boolean]) {
    def ifFalse(f: => DomainError)(implicit builder: AsyncTransactionBuilder): AsyncTransaction[Unit] = {
      transactionBool.flatMap(bool => {
        val either = if (bool) \/-(()) else -\/(f)
        builder.execute(either)
      })
    }
  }

  implicit class SyncTransactionOptionErrorHandler[A](transactionOpt: SyncTransaction[Option[A]]) {
    def ifNotExists(f: => DomainError)(implicit builder: SyncTransactionBuilder): SyncTransaction[A] = {
      transactionOpt.flatMap(opt => builder.execute(opt \/> f))
    }
  }

}
