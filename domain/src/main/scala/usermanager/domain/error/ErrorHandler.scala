package usermanager.domain.error

import usermanager.domain.transaction.delete.Deleted
import usermanager.domain.transaction.{ Transaction, TransactionBuilder }

import scalaz.syntax.std.ToOptionOps
import scalaz.{ -\/, \/- }

trait ErrorHandler extends ToOptionOps {

  implicit class TransactionOptionErrorHandler[A](transactionOpt: Transaction[Option[A]]) {
    def ifNotExists(f: => DomainError)(implicit builder: TransactionBuilder): Transaction[A] = {
      transactionOpt.flatMap(opt => builder.build(opt \/> f))
    }

    def ifExists(f: => DomainError)(implicit builder: TransactionBuilder): Transaction[Unit] = {
      transactionOpt.flatMap(opt => builder.build(opt match {
        case Some(_) => -\/(f)
        case None => \/-()
      }))
    }
  }

  implicit class TransactionBooleanErrorHandler(transactionDeleted: Transaction[Deleted]) {
    def ifNotDeleted(f: => DomainError)(implicit builder: TransactionBuilder): Transaction[Unit] = {
      transactionDeleted.flatMap(deleted => {
        val either = if (deleted) \/-(()) else -\/(f)
        builder.build(either)
      })
    }
  }

}
