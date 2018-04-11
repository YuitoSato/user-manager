package usermanager.domain.transaction.sync

import usermanager.domain.error.DomainError
import usermanager.domain.transaction.TransactionBuilder

import scalaz.\/

trait SyncTransactionBuilder extends TransactionBuilder {

  def execute[A](value: DomainError \/ A): SyncTransaction[A]

  def execute[A](value: A): SyncTransaction[A]

}
