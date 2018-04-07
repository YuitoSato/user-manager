package usermanager.domain.transaction.async

import usermanager.domain.error.DomainError
import usermanager.domain.transaction.TransactionBuilder

import scalaz.\/

trait AsyncTransactionBuilder extends TransactionBuilder {

  def exec[A](value: DomainError \/ A): AsyncTransaction[A]

  def exec[A](value: A): AsyncTransaction[A]

}
