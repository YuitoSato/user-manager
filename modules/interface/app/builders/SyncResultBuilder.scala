package builders

import javax.inject.Singleton
import usermanager.domain.result.{ Result, ResultBuilder, SyncResult }
import scalaz.{ \/, \/- }
import usermanager.lib.error.Error

@Singleton
class SyncResultBuilder extends ResultBuilder{

  override def build[A](value: \/[Error, A]): Result[A] = {
    SyncResult(value)
  }

  override def build[A](value: A): Result[A] = {
    SyncResult(\/-(value))
  }

}
