package usermanager.domain.session

import usermanager.domain.types.Id

trait SessionRepository[F[_]] {

  def findById(sessionId: Id[Session]): F[Option[Session]]

  def create(session: Session): F[Unit]

  def delete(sessionId: Id[Session]): F[Unit]

}
