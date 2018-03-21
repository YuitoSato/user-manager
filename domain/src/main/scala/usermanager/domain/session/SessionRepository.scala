package usermanager.domain.session

import usermanager.domain.error.AbstractError
import usermanager.domain.types.Id

import scalaz.EitherT

trait SessionRepository[F[_]] {

  def findById(sessionId: Id[Session]): EitherT[F, AbstractError, Option[Session]]

  def create(session: Session): EitherT[F, AbstractError, Unit]

  def delete(sessionId: Id[Session]): EitherT[F, AbstractError, Unit]

}
