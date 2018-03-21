package syntax

import usermanager.application.error.ApplicationError

import scalaz.{ EitherT, \/ }

// NOTE: traitにしておけば継承してimplicitを使える
trait ToEitherOps {

  implicit class DisjunctionToEitherOps[F[_], A](fa: F[ApplicationError \/ A]) {
    def et: EitherT[F, ApplicationError, A] = {
      EitherT(fa)
    }
  }
}
