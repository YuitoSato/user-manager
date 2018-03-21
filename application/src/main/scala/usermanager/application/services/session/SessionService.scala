package usermanager.application.services.session

import usermanager.domain.session.{ Session, SessionRepository }
import usermanager.domain.types.Id

import scalaz.Monad

trait SessionService[F[_] <: Monad[_]] {

  val sessionRepository: SessionRepository[F]

  def findById(sessionId: Id[Session]): F[Option[Session]] = sessionRepository.findById(sessionId)







}
