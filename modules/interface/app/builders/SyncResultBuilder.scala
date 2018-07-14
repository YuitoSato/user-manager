package builders

import javax.inject.Singleton
import scalaz.{ \/, \/- }
import usermanager.lib.error.Error
import usermanager.lib.result.{ Result, ResultBuilder, SyncResult }

@Singleton
class SyncResultBuilder extends ResultBuilder {

  override def build[A](value: \/[Error, A]): Result[A] = {
    SyncResult(value)
  }

  override def build[A](value: A): Result[A] = {
    SyncResult(\/-(value))
  }

}
