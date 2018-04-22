package usermanager.domain.transaction

import usermanager.domain.error.DomainError

import scalaz.\/

trait TransactionBuilder {

  def build[A](value: \/[DomainError, A]): Transaction[A]

  def build[A](value: A): Transaction[A]

}
