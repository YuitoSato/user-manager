package usermanager.domain.error

import usermanager.domain.transaction.{ Transaction, TransactionBuilder }

import scalaz.syntax.std.ToOptionOps
import scalaz.{ -\/, \/- }

trait ErrorHandler extends ToOptionOps {

  implicit class TransactionOptionErrorHandler[A](transactionOpt: Transaction[Option[A]]) {
    def ifNotExists(f: => DomainError)(implicit builder: TransactionBuilder): Transaction[A] = {
      transactionOpt.flatMap(opt => builder.execute(opt \/> f))
    }
  }

  implicit class TransactionBooleanErrorHandler(transactionBool: Transaction[Boolean]) {
    def ifFalse(f: => DomainError)(implicit builder: TransactionBuilder): Transaction[Unit] = {
      transactionBool.flatMap(bool => {
        val either = if (bool) \/-(()) else -\/(f)
        builder.execute(either)
      })
    }
  }

}
