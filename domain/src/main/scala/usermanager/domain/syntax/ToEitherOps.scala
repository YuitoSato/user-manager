package usermanager.domain.syntax

import usermanager.domain.error.DomainError

import scala.concurrent.Future
import scalaz.{ EitherT, \/, \/- }
import scalaz.syntax.std.ToOptionOps

trait ToEitherOps {

  implicit class DisjunctionToEitherOps[F[_], A](fa: F[DomainError \/ A]) {
    def et: EitherT[F, DomainError, A] = {
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
