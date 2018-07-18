package usermanager.lib.result

import scalaz.{ -\/, \/, \/- }
import scalaz.std.EitherInstances
import usermanager.lib.error.Error

case class MockResult[A](
  value: Error \/ A
) extends Result[A] with EitherInstances { self =>

  override def map[B](f: A => B): Result[B] = MockResult(value.map(f))

  override def flatMap[B](f: A => Result[B]): Result[B] = {
    MockResult(
      value.flatMap(f(_).asInstanceOf[MockResult[B]].value)
    )
  }

  override def leftMap(f: Error => Error): Result[A] = MockResult(value.leftMap(f))



}

object MockResult {

  def apply[A](value: A): Result[A] = {
    MockResult(\/-(value))
  }

  def error[A](error: Error): Result[A] = {
    MockResult(-\/(error))
  }

}
