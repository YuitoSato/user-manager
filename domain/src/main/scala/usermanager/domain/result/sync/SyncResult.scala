package usermanager.domain.result.sync

import usermanager.domain.error.DomainError
import usermanager.domain.result.Result
import usermanager.domain.result.async.AsyncResult
import usermanager.domain.syntax.ToEitherOps

import scala.concurrent.{ ExecutionContext, Future }
import scalaz.{ -\/, \/, \/- }

case class SyncResult[A](value: DomainError \/ A) extends Result[A] with ToEitherOps { self =>

  override def map[B](f: A => B): SyncResult[B] = SyncResult(value.map(f))

  override def flatMap[B](f: A => Result[B]): SyncResult[B] = SyncResult(value.flatMap(f(_).asInstanceOf[SyncResult[B]].value))

  def toAsync(implicit ec: ExecutionContext): AsyncResult[A] = AsyncResult(Future.successful(self.value).et)

}

object SyncResult {

  def apply[A](value: A): SyncResult[A] = {
    SyncResult(\/-(value))
  }

  def error[A](error: DomainError): SyncResult[A] = {
    SyncResult(-\/(error))
  }
}
