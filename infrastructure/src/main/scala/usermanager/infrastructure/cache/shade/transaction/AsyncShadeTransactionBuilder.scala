package usermanager.infrastructure.cache.shade.transaction

import javax.inject.Inject

import usermanager.domain.error.DomainError
import usermanager.domain.syntax.ToEitherOps
import usermanager.domain.transaction.{ Transaction, TransactionBuilder }

import scala.concurrent.{ ExecutionContext, Future }
import scalaz.{ \/, \/- }

class AsyncShadeTransactionBuilder @Inject()(implicit ec: ExecutionContext) extends TransactionBuilder with ToEitherOps {

  override def execute[A](value: \/[DomainError, A]): Transaction[A] = AsyncShadeTransaction(Future.successful(value).et)

  override def execute[A](value: A): Transaction[A] = {
    val either: Future[DomainError \/ A] = Future.successful(\/-(value))
    AsyncShadeTransaction(either.et)
  }

}
