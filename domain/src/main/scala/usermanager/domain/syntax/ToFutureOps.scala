package usermanager.domain.syntax

import scala.concurrent.Future

trait ToFutureOps {

  implicit class ObjectToEitherOps[A](value: A) {
    def future: Future[A] = {
      Future.successful(value)
    }
  }

}
