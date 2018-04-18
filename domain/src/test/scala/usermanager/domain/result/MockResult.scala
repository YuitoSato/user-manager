package usermanager.domain.result

import usermanager.domain.error.DomainError

import scalaz.\/

case class MockResult[A](value: DomainError \/ A) extends Result[A] {

  override def map[B](f: A => B) = MockResult(value.map(f))

  override def flatMap[B](f: A => Result[B]) = MockResult(value.flatMap(f(_).asInstanceOf[MockResult[B]].value))

}
