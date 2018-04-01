package usermanager.application.services.session

import javax.inject.Inject

import usermanager.domain.error.{ DomainError, ErrorHandler }
import usermanager.domain.session.{ Session, SessionRepository }
import usermanager.domain.syntax.ToEitherOps
import usermanager.domain.types.Id

import scala.concurrent.Future
import scalaz.{ EitherT, \/ }

class SessionService @Inject()(
  sessionRepository: SessionRepository
) extends ToEitherOps with ErrorHandler {

  def awaitFindById(sessionId: Id[Session]): Either[DomainError \/ Session] = {
    sessionRepository
  }

  def findById(sessionId: Id[Session]): EitherT[Future, DomainError, Session] = {
    sessionRepository.find(sessionId) ifNotExists DomainError.NotFound("Session", sessionId)
  }

  def create(session: Session): EitherT[Future, DomainError, Unit] = {
    sessionRepository.create(session).et
  }

  def delete(sessionId: Id[Session]): EitherT[Future, DomainError, Unit] = {
    sessionRepository.delete(sessionId) ifFalse DomainError.NotFound("Session", sessionId)
  }

}
