package usermanager.application.services.session

import javax.inject.Inject

import usermanager.application.error.ApplicationError
import usermanager.domain.session.{ Session, SessionRepository }
import usermanager.domain.types.Id

import scala.util.Try
import scalaz.EitherT

class SessionService @Inject()(
  sessionRepository: SessionRepository[Try]
) {

  def findById(sessionId: Id[Session]): EitherT[Try, ApplicationError, Option[Session]] = sessionRepository.findById(sessionId)

  def create(session: Session): Try[Unit] = sessionRepository.create(session)

  def delete(sessionId: Id[Session]): Try[Unit] = sessionRepository.delete(sessionId)

}
