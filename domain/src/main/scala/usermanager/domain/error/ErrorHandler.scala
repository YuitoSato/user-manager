package usermanager.domain.error

import scala.concurrent.Future
import scalaz.{ -\/, EitherT, \/- }
import scalaz.syntax.std.ToOptionOps

trait ErrorHandler extends ToOptionOps {

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

}
