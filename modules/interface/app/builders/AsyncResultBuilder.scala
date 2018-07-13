package builders

import javax.inject.{ Inject, Singleton }

import usermanager.domain.error.Error
import usermanager.domain.result.{ AsyncResult, Result, ResultBuilder }
import usermanager.domain.syntax.{ ToEitherOps, ToFutureOps }

import scala.concurrent.ExecutionContext
import scalaz.{ \/, \/- }

@Singleton
class AsyncResultBuilder @Inject()(
  implicit val ec: ExecutionContext
) extends ResultBuilder with ToFutureOps with ToEitherOps {

  override def build[A](value: \/[Error, A]): Result[A] = {
    AsyncResult(value.future.et)
  }

  override def build[A](value: A): Result[A] = {
    val either: Error \/ A = \/-(value)
    AsyncResult(either.future.et)
  }

}
