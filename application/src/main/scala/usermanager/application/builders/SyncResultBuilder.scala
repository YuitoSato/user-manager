package usermanager.application.builders

import usermanager.domain.result.{ Result, ResultBuilder, SyncResult }

import scalaz.{ \/, \/- }
import javax.inject.Singleton

import usermanager.domain.error.DomainError


@Singleton
class SyncResultBuilder extends ResultBuilder{

  override def execute[A](value: \/[DomainError, A]): Result[A] = {
    SyncResult(value)
  }

  override def execute[A](value: A): Result[A] = {
    SyncResult(\/-(value))
  }

}
