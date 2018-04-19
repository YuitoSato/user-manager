package usermanager.application.services.session

import usermanager.application.services.ServiceBase
import usermanager.domain.aggregates.sessionuser.{ SessionUser, SessionUserRepository }
import usermanager.domain.error.DomainError
import usermanager.domain.transaction.Transaction
import usermanager.domain.types.Id

trait SessionService extends ServiceBase {

  val sessionRepository: SessionUserRepository

  def findById(sessionId: Id[SessionUser]): Transaction[SessionUser] = {
    sessionRepository.find(sessionId) ifNotExists DomainError.NotFound(SessionUser.TYPE, sessionId)
  }

  def create(session: SessionUser): Transaction[Unit] = {
    sessionRepository.create(session)
  }

  def delete(sessionId: Id[SessionUser]): Transaction[Unit] = {
    sessionRepository.delete(sessionId) ifNotDeleted DomainError.NotFound(SessionUser.TYPE, sessionId)
  }

}
