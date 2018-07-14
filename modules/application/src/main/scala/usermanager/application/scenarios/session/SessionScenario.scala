package usermanager.application.scenarios.session

import usermanager.application.services.session.SessionService
import usermanager.domain.aggregates.sessionuser.SessionUser
import usermanager.domain.types.Id
import usermanager.lib.result.Result

trait SessionScenario {

  val sessionService: SessionService

  def findById(sessionId: Id[SessionUser]): Result[SessionUser] = {
    sessionService.findById(sessionId).run
  }

  def create(session: SessionUser): Result[Unit] = {
    sessionService.create(session).run
  }

  def delete(sessionId: Id[SessionUser]): Result[Unit] = {
    sessionService.delete(sessionId).run
  }

}
