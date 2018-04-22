package usermanager.domain.result

import usermanager.domain.error.DomainError

import scalaz.\/

trait ResultBuilder {

  def build[A](value: DomainError \/ A): Result[A]

  def build[A](value: A): Result[A]

}
