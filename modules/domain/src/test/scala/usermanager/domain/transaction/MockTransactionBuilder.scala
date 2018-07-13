package usermanager.domain.transaction
import usermanager.domain.error.Error

import scalaz.{ \/, \/- }

class MockTransactionBuilder extends TransactionBuilder {

  override def build[A](value: \/[Error, A]): Transaction[A] = {
    val exec = () => value
    MockTransaction(exec)
  }

  override def build[A](value: A): Transaction[A] = {
    val exec = () => \/-(value)
    MockTransaction(exec)
  }

}
