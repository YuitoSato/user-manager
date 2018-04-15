package usermanager.domain.result

import usermanager.domain.error.DomainError

import scalaz.\/

case class MockResult[A](value: DomainError \/ A) {

}
