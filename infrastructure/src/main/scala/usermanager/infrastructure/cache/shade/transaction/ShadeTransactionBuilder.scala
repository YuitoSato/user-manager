package usermanager.infrastructure.cache.shade.transaction

import usermanager.domain.transaction.TransactionBuilder
import usermanager.domain.transaction.async.{ AsyncTransaction, AsyncTransactionBuilder }

class ShadeTransactionBuilder extends TransactionBuilder {

  override def exec[A](value: A): AsyncTransaction[A] = ShadeTransaction(value)

}
