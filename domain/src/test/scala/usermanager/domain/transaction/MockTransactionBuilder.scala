package usermanager.domain.transaction
import usermanager.domain.error.DomainError

import scalaz.{ \/, \/- }

class MockTransactionBuilder extends TransactionBuilder {

  override def build[A](value: \/[DomainError, A]): Transaction[A] = {
    val exec = () => value
    MockTransaction(exec)
  }

  override def build[A](value: A): Transaction[A] = {
    val exec = () => \/-(value)
    MockTransaction(exec)
  }

}
