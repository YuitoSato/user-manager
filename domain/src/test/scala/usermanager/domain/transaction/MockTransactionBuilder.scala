package usermanager.domain.transaction
import usermanager.domain.error.DomainError

import scalaz.{ \/, \/- }

class MockTransactionBuilder extends TransactionBuilder {

  override def execute[A](value: \/[DomainError, A]): Transaction[A] = MockTransaction(value)

  override def execute[A](value: A): Transaction[A] = MockTransaction(\/-(value))

}
