package usermanager.domain.transaction
import usermanager.domain.error.DomainError

import scalaz.\/

class MockTransactionBuilder extends TransactionBuilder {

  override def execute[A](value: \/[DomainError, A]) = ???

  override def execute[A](value: A) = ???
  
}
