package usermanager.domain.syntax

import usermanager.domain.error.DomainError
import usermanager.domain.transaction.Transaction

import scalaz.{ EitherT, \/, \/- }
import scalaz.syntax.std.ToOptionOps

trait ToEitherOps extends ToOptionOps {

  implicit class DisjunctionToEitherOps[F[_], A](fa: F[DomainError \/ A]) {
    def et: EitherT[F, DomainError, A] = {
      EitherT(fa)
    }
  }

  implicit class TransactionOptToEitherOps[A](transactionOpt: Transaction[Option[A]]) {
    def ifNotExists(f: => DomainError): EitherT[Transaction, DomainError, A] = transactionOpt.map(_ \/> f).et
  }

  implicit class TransactionUnitToEitherOps[A](transactionUnit: Transaction[Unit]) {
    def et: EitherT[Transaction, DomainError, Unit] = EitherT(transactionUnit.map(\/.right))
  }
}
