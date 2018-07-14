package usermanager.infrastructure.cache.shade.transaction

import javax.inject.Inject
import usermanager.domain.syntax.ToEitherOps
import usermanager.domain.transaction.Transaction

import scala.concurrent.{ ExecutionContext, Future }
import scalaz.{ \/, \/- }
import usermanager.lib.error
import usermanager.lib.error.Error
import usermanager.lib.error.transaction.{ Transaction, TransactionBuilder }

class ShadeTransactionBuilder @Inject()(implicit ec: ExecutionContext) extends TransactionBuilder with ToEitherOps {

  override def build[A](value: \/[error.Error, A]): Transaction[A] = {
    val future: Future[error.Error \/ A] = Future.successful(value)
    val exec = () => future.et
    ShadeTransaction(exec)
  }

  override def build[A](value: A): Transaction[A] = {
    val future: Future[Error \/ A] = Future.successful(\/-(value))
    val exec = () => future.et
    ShadeTransaction(exec)
  }

}
