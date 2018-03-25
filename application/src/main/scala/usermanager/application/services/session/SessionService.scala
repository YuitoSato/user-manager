package usermanager.application.services.session

import javax.inject.Inject

import usermanager.domain.session.{ Session, SessionRepository }
import usermanager.domain.syntax.ToEitherOps
import usermanager.domain.transaction.Transaction
import usermanager.domain.types.Id
import usermanager.domain.error.DomainError

import scalaz.EitherT

class SessionService @Inject()(
  sessionRepository: SessionRepository
) extends ToEitherOps {

  def findById(sessionId: Id[Session]): EitherT[Transaction, DomainError, Session] =
    sessionRepository.findById(sessionId) ifNotExists DomainError.NotFound("Session", sessionId)

  def create(session: Session): EitherT[Transaction, DomainError, Unit] = sessionRepository.create(session).et

  def delete(sessionId: Id[Session]): EitherT[Transaction, DomainError, Unit] = sessionRepository.delete(sessionId).et

}
