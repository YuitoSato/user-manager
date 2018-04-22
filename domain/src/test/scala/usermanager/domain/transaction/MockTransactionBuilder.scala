package usermanager.domain.transaction
import usermanager.domain.error.DomainError

import scalaz.{ \/, \/- }

class MockTransactionBuilder extends TransactionBuilder {

  override def build[A](value: \/[DomainError, A]): Transaction[A] = MockTransaction(value)

  override def build[A](value: A): Transaction[A] = MockTransaction(\/-(value))

}
