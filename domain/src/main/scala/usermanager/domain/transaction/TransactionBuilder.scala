package usermanager.domain.transaction

import usermanager.domain.error.DomainError

import scalaz.\/

trait TransactionBuilder {

  def execute[A](value: \/[DomainError, A]): Transaction[A]

  def execute[A](value: A): Transaction[A]

}
