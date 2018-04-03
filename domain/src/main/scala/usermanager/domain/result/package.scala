package usermanager.domain

import usermanager.domain.error.DomainError
import usermanager.domain.syntax.ToEitherOps
import usermanager.domain.transaction.TransactionBuilder
import usermanager.domain.transaction.async.{ AsyncTransaction, AsyncTransactionBuilder }

import scala.concurrent.Future
import scalaz.{ -\/, EitherT, \/, \/- }

package object result {

  type AsyncResult[A] = EitherT[Future, DomainError, A]
  type SyncResult[A] = DomainError \/ A
  type AsyncTransactionResult[A] = EitherT[AsyncTransaction, DomainError, A]


  object AsyncResult extends ToEitherOps {

    def apply[A](value: A): AsyncResult[A] = {
      val either: DomainError \/ A = \/-(value)
      Future.successful(either).et
    }

    def error[A](error: DomainError): AsyncResult[A] = {
      val either: DomainError \/ A = -\/(error)
      Future.successful(either).et
    }
  }

  object SyncResult {

    def apply[A](value: A): SyncResult[A] = {
      \/-(value)
    }

    def error[A](error: DomainError): SyncResult[A] = {
      -\/(error)
    }
  }


  object TransactionResult extends ToEitherOps {

    def apply[A](dbio: AsyncTransaction[DomainError \/ A]): AsyncTransactionResult[A] = {
      dbio.et
    }

    def apply[A](value: A)(implicit builder: AsyncTransactionBuilder): AsyncTransactionResult[A] = {
      val a = builder.exec(value)
      val dbio: AsyncTransaction[DomainError \/ A] = builder.exec(value).map(\/.right)
      TransactionResult(dbio)
    }

    def apply[A](either: DomainError \/ A)(implicit builder: AsyncTransactionBuilder): AsyncTransactionResult[A] = {
      TransactionResult(builder.exec(either))
    }
  }

}
