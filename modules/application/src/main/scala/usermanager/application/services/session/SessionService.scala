package usermanager.application.services.session

import usermanager.application.services.ServiceBase
import usermanager.domain.aggregates.sessionuser.{ SessionRepository, SessionUser }
import usermanager.domain.types.Id
import usermanager.lib.error.Error
import usermanager.lib.error.transaction.Transaction

trait SessionService extends ServiceBase {

  val sessionRepository: SessionRepository

  def findById(sessionId: Id[SessionUser]): Transaction[SessionUser] = {
    sessionRepository.find(sessionId) assertNotExists Error.NotFound(SessionUser.TYPE, sessionId)
  }

  def create(session: SessionUser): Transaction[Unit] = {
    sessionRepository.create(session)
  }

  def delete(sessionId: Id[SessionUser]): Transaction[Unit] = {
    sessionRepository.delete(sessionId) assertNotDeleted Error.NotFound(SessionUser.TYPE, sessionId)
  }

}
