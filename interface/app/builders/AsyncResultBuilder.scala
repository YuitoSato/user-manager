package builders

import javax.inject.{ Inject, Singleton }

import usermanager.domain.error.DomainError
import usermanager.domain.result.{ AsyncResult, Result, ResultBuilder }
import usermanager.domain.syntax.{ ToEitherOps, ToFutureOps }

import scala.concurrent.ExecutionContext
import scalaz.{ \/, \/- }

@Singleton
class AsyncResultBuilder @Inject()(
  implicit val ec: ExecutionContext
) extends ResultBuilder with ToFutureOps with ToEitherOps {

  override def build[A](value: \/[DomainError, A]): Result[A] = {
    AsyncResult(value.future.et)
  }

  override def build[A](value: A): Result[A] = {
    val either: DomainError \/ A = \/-(value)
    AsyncResult(either.future.et)
  }

}
