package usermanager.domain.result.async

import usermanager.domain.error.DomainError
import usermanager.domain.result.TransactionResult
import usermanager.domain.syntax.ToEitherOps
import usermanager.domain.transaction.async.{ AsyncTransaction, AsyncTransactionBuilder }

import scalaz.{ EitherT, \/ }

case class AsyncTransactionResult[+A](value: EitherT[AsyncTransaction, DomainError, A]) extends TransactionResult[A] {

  override def map[B](f: A => B): TransactionResult[B] = AsyncTransactionResult(value.map(f))

  override def flatMap[B](f: A => TransactionResult[B]): AsyncTransactionResult[B] = {
    f(value).asInstanceOf[AsyncTransactionResult[B]]
  }

}

object AsyncTransactionResult extends ToEitherOps {

  def apply[A](transaction: AsyncTransaction[DomainError \/ A]): AsyncTransactionResult[A] = {
    AsyncTransactionResult(transaction.et)
  }

  def apply[A](value: A)(implicit builder: AsyncTransactionBuilder): AsyncTransactionResult[A] = {
    AsyncTransactionResult(builder.exec(value).map(\/.right))
  }

  def apply[A](either: DomainError \/ A)(implicit builder: AsyncTransactionBuilder): AsyncTransactionResult[A] = {
    AsyncTransactionResult(builder.exec(either))
  }

}
