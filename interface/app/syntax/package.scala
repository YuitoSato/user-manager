
import usermanager.application.error.ApplicationError

import scala.concurrent.Future
import scalaz.{ -\/, EitherT, \/, \/- }

package object syntax {

  type Result[A] = EitherT[Future, ApplicationError, A]
//  type DBResult[A] = EitherT[Transaction, DomainError, A]

  object Result extends ToEitherOps {

    def apply[A](value: A): Result[A] = {
      val either: ApplicationError \/ A = \/-(value)
      Future.successful(either).et
    }

    def error[A](error: ApplicationError): Result[A] = {
      val either: ApplicationError \/ A = -\/(error)
      Future.successful(either).et
    }
  }
//
//  object DBResult extends ToEitherOps {
//
//    def apply[A](dbio: Transaction[DomainError \/ A]): DBResult[A] = {
//      dbio.et
//    }
//
//    def apply[A](value: A)(implicit builder: TransactionBuilder): DBResult[A] = {
//      val dbio: Transaction[DomainError \/ A] = builder.exec(value).map(\/.right)
//      DBResult(dbio)
//    }
//
//    def apply[A](either: DomainError \/ A)(implicit builder: TransactionBuilder): DBResult[A] = {
//      DBResult(builder.exec(either))
//    }
//  }
}
