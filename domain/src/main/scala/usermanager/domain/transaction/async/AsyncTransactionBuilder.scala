package usermanager.domain.transaction.async

import usermanager.domain.transaction.TransactionBuilder

trait AsyncTransactionBuilder extends TransactionBuilder {

  def exec[A](value: A): AsyncTransaction[A]

}
