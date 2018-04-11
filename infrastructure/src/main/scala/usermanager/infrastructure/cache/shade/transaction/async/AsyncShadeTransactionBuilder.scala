package usermanager.infrastructure.cache.shade.transaction.async

import javax.inject.Inject

import usermanager.domain.error.DomainError
import usermanager.domain.syntax.ToEitherOps
import usermanager.domain.transaction.async.{ AsyncTransaction, AsyncTransactionBuilder }

import scala.concurrent.{ ExecutionContext, Future }
import scalaz.{ \/, \/- }

class AsyncShadeTransactionBuilder @Inject()(implicit ec: ExecutionContext) extends AsyncTransactionBuilder with ToEitherOps {

  override def execute[A](value: \/[DomainError, A]): AsyncTransaction[A] = AsyncShadeTransaction(Future.successful(value).et)

  override def execute[A](value: A): AsyncTransaction[A] = {
    val either: Future[DomainError \/ A] = Future.successful(\/-(value))
    AsyncShadeTransaction(either.et)
  }

}
