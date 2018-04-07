package usermanager.infrastructure.cache.shade.transaction

import usermanager.domain.error.DomainError
import usermanager.domain.syntax.ToEitherOps
import usermanager.domain.transaction.async.{ AsyncTransaction, AsyncTransactionBuilder }

import scala.concurrent.{ ExecutionContext, Future }
import scalaz.{ \/, \/- }

class ShadeTransactionBuilder()(implicit ec: ExecutionContext) extends AsyncTransactionBuilder with ToEitherOps {

  override def exec[A](value: \/[DomainError, A]): AsyncTransaction[A] = ShadeTransaction(Future.successful(value).et)

  override def exec[A](value: A): AsyncTransaction[A] = {
    val either: Future[DomainError \/ A] = Future.successful(\/-(value))
    ShadeTransaction(either.et)
  }

}
