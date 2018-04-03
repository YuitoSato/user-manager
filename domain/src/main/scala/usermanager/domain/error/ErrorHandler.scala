package usermanager.domain.error

import usermanager.domain.result.AsyncTransactionResult
import usermanager.domain.transaction.async.AsyncTransaction

import scala.concurrent.Future
import scalaz.syntax.std.ToOptionOps
import scalaz.{ -\/, EitherT, \/- }

trait ErrorHandler extends ToOptionOps {

  implicit class AsyncTransactionOptionErrorHandler[A](transactionOpt: AsyncTransaction[Option[A]]) {
    def ifNotExists(f: => DomainError): AsyncTransactionResult[A] = {
      EitherT(transactionOpt.map(_ \/> f))
    }
  }

  implicit class FutureOptionErrorHandler[A](futureOpt: Future[Option[A]]) {
    def ifNotExists(f: => DomainError): EitherT[Future, DomainError, A] = {
      EitherT(futureOpt.map(_ \/> f))
    }
  }

  implicit class FutureBooleanErrorHandler(futureBool: Future[Boolean]) {
    def ifFalse(f: => DomainError): EitherT[Future, DomainError, Unit] = {
      EitherT {
        futureBool.map(bool =>
          if (bool) {
            \/-(())
          } else {
            -\/(f)
          }
        )
      }
    }
  }

  implicit class OptionErrorHandler[A](opt: Option[])

}
