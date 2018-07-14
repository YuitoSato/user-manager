package usermanager.lib.error

import scalaz.syntax.std.ToOptionOps
import scalaz.{ -\/, \/- }
import usermanager.lib.error.transaction.delete.Deleted
import usermanager.lib.transaction.{ Transaction, TransactionBuilder }

trait ErrorHandler extends ToOptionOps {

  implicit class TransactionOptionErrorHandler[A](transactionOpt: Transaction[Option[A]]) {
    def assertNotExists(f: => Error)(implicit builder: TransactionBuilder): Transaction[A] = {
      transactionOpt.flatMap(opt => builder.build(opt \/> f))
    }

    def assertExists(f: => Error)(implicit builder: TransactionBuilder): Transaction[Unit] = {
      transactionOpt.flatMap(opt => builder.build(opt match {
        case Some(_) => -\/(f)
        case None => \/-()
      }))
    }
  }

  implicit class TransactionBooleanErrorHandler(transactionDeleted: Transaction[Deleted]) {
    def assertNotDeleted(f: => Error)(implicit builder: TransactionBuilder): Transaction[Unit] = {
      transactionDeleted.flatMap(deleted => {
        val either = if (deleted) \/-(()) else -\/(f)
        builder.build(either)
      })
    }
  }

}
