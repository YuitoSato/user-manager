package usermanager.domain.result.sync

import usermanager.domain.error.DomainError
import usermanager.domain.result.Result

import scalaz.{ -\/, \/, \/- }

case class SyncResult[+A](value: DomainError \/ A) extends Result[A] {

  override def map[B](f: A => B): SyncResult[B] = SyncResult(value.map(f))

  override def flatMap[B](f: A => Result[B]): SyncResult[B] = f(value).asInstanceOf[SyncResult[B]]

}

object SyncResult {

  def apply[A](value: A): SyncResult[A] = {
    SyncResult(\/-(value))
  }

  def error[A](error: DomainError): SyncResult[A] = {
    SyncResult(-\/(error))
  }
}
