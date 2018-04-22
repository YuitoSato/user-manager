package usermanager.infrastructure.cache.shade.transaction

import javax.inject.Inject

import usermanager.domain.error.DomainError
import usermanager.domain.syntax.ToEitherOps
import usermanager.domain.transaction.{ Transaction, TransactionBuilder }

import scala.concurrent.{ ExecutionContext, Future }
import scalaz.{ \/, \/- }

class ShadeTransactionBuilder @Inject()(implicit ec: ExecutionContext) extends TransactionBuilder with ToEitherOps {

  override def execute[A](value: \/[DomainError, A]): Transaction[A] = {
    val future: Future[DomainError \/ A] = Future.successful(value)
    val exec = () => future.et
    ShadeTransaction(exec)
  }

  override def execute[A](value: A): Transaction[A] = {
    val future: Future[DomainError \/ A] = Future.successful(\/-(value))
    val exec = () => future.et
    ShadeTransaction(exec)
  }

}
