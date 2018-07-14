package usermanager.domain.syntax

import scalaz.{ EitherT, \/ }
import usermanager.lib.error
import usermanager.lib.error.Error

trait ToEitherOps {

  implicit class DisjunctionToEitherOps[F[_], A](fa: F[error.Error \/ A]) {
    def et: EitherT[F, Error, A] = {
      EitherT(fa)
    }
  }
//
//  implicit class ObjectToEitherOps[A](value: A) {
//    def et: EitherT[Future, DomainError, A] = {
//      val either: DomainError \/ A = \/-(value)
//      EitherT(Future.successful(either))
//    }
//  }

//  implicit class TransactionOptToEitherOps[A](transactionOpt: Transaction[Option[A]]) {
//    def ifNotExists(f: => DomainError): EitherT[Transaction, DomainError, A] = transactionOpt.map(_ \/> f).et
//  }
//
//  implicit class TransactionUnitToEitherOps[A](transactionUnit: Transaction[Unit]) {
//    def et: EitherT[Transaction, DomainError, Unit] = EitherT(transactionUnit.map(\/.right))
//  }
}
