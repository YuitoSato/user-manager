package usermanager.domain.transaction.sync

import usermanager.domain.transaction.TransactionBuilder

trait SyncTransactionBuilder extends TransactionBuilder {

  def exec[A](value: A): SyncTransaction[A]

}
