package usermanager.domain.result.sync

import usermanager.domain.error.DomainError
import usermanager.domain.result.TransactionResult
import usermanager.domain.syntax.ToEitherOps
import usermanager.domain.transaction.sync.{ SyncTransaction, SyncTransactionBuilder }

import scalaz.{ EitherT, \/ }

case class SyncTransactionResult[+A](value: EitherT[SyncTransaction, DomainError, A]) extends TransactionResult[A] {

  override def map[B](f: A => B): TransactionResult[B] = SyncTransactionResult(value.map(f))

  override def flatMap[B](f: A => TransactionResult[B]): SyncTransactionResult[B] = {
    f(value).asInstanceOf[SyncTransactionResult[B]]
  }

}

object SyncTransactionResult extends ToEitherOps {

  def apply[A](transaction: SyncTransaction[DomainError \/ A]): SyncTransactionResult[A] = {
    SyncTransactionResult(transaction.et)
  }

  def apply[A](value: A)(implicit builder: SyncTransactionBuilder): SyncTransactionResult[A] = {
    SyncTransactionResult(builder.exec(value).map(\/.right))
  }

  def apply[A](either: DomainError \/ A)(implicit builder: SyncTransactionBuilder): SyncTransactionResult[A] = {
    SyncTransactionResult(builder.exec(either))
  }

}
