package usermanager.domain.result

import usermanager.domain.error.DomainError

import scalaz.\/

trait ResultBuilder {

  def execute[A](value: DomainError \/ A): Result[A]

  def execute[A](value: A): Result[A]

}
