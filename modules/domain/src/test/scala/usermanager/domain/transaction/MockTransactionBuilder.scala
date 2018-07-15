package usermanager.domain.transaction

import scalaz.{ \/, \/- }
import usermanager.lib.error.Error
import usermanager.lib.transaction.{ Transaction, TransactionBuilder }

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
