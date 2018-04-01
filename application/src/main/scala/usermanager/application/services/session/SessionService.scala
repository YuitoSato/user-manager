package usermanager.application.services.session

import javax.inject.Inject

import usermanager.domain.error.{ DomainError, ErrorHandler }
import usermanager.domain.sessionuser.{ SessionUser, SessionUserRepository }
import usermanager.domain.syntax.ToEitherOps
import usermanager.domain.types.Id

import scala.concurrent.Future
import scalaz.{ EitherT, \/ }

class SessionService @Inject()(
  sessionRepository: SessionUserRepository
) extends ToEitherOps with ErrorHandler {

  def awaitFindById(sessionId: Id[SessionUser]): Either[DomainError \/ SessionUser] = {
    sessionRepository
  }

  def findById(sessionId: Id[SessionUser]): EitherT[Future, DomainError, SessionUser] = {
    sessionRepository.find(sessionId) ifNotExists DomainError.NotFound("Session", sessionId)
  }

  def create(session: SessionUser): EitherT[Future, DomainError, Unit] = {
    sessionRepository.create(session).et
  }

  def delete(sessionId: Id[SessionUser]): EitherT[Future, DomainError, Unit] = {
    sessionRepository.delete(sessionId) ifFalse DomainError.NotFound("Session", sessionId)
  }

}
