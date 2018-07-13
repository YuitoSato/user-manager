package usermanager.infrastructure.cache.shade.transaction

import javax.inject.Inject

import usermanager.domain.error.Error
import usermanager.domain.syntax.ToEitherOps
import usermanager.domain.transaction.{ Transaction, TransactionBuilder }

import scala.concurrent.{ ExecutionContext, Future }
import scalaz.{ \/, \/- }

class ShadeTransactionBuilder @Inject()(implicit ec: ExecutionContext) extends TransactionBuilder with ToEitherOps {

  override def build[A](value: \/[Error, A]): Transaction[A] = {
    val future: Future[Error \/ A] = Future.successful(value)
    val exec = () => future.et
    ShadeTransaction(exec)
  }

  override def build[A](value: A): Transaction[A] = {
    val future: Future[Error \/ A] = Future.successful(\/-(value))
    val exec = () => future.et
    ShadeTransaction(exec)
  }

}
