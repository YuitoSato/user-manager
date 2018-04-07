package usermanager.application.scenarios.session

import javax.inject.Inject

import usermanager.application.services.session.SessionService
import usermanager.domain.result.async.AsyncResult
import usermanager.domain.result.sync.SyncResult
import usermanager.domain.aggregates.sessionuser.SessionUser
import usermanager.domain.transaction.async.AsyncTransactionRunner
import usermanager.domain.transaction.sync.SyncTransactionRunner
import usermanager.domain.types.Id

import scala.concurrent.ExecutionContext

class SessionScenario @Inject()(
  sessionService: SessionService
)(
  implicit ec: ExecutionContext,
  implicit val syncTransactionRunner: SyncTransactionRunner,
  implicit val asyncTransactionRunner: AsyncTransactionRunner
) {

  def awaitFindById(sessionId: Id[SessionUser]): SyncResult[SessionUser] = {
    sessionService.awaitFindById(sessionId).run
  }

  def findById(sessionId: Id[SessionUser]): AsyncResult[SessionUser] = {
    sessionService.findById(sessionId).run
  }

  def create(session: SessionUser): AsyncResult[Unit] = {
    sessionService.create(session).run
  }

  def delete(sessionId: Id[SessionUser]): AsyncResult[Unit] = {
    sessionService.delete(sessionId).run
  }

}
