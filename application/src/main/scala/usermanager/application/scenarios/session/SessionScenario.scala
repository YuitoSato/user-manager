package usermanager.application.scenarios.session

import javax.inject.{ Inject, Named }

import usermanager.application.services.session.SessionService
import usermanager.domain.aggregates.sessionuser.SessionUser
import usermanager.domain.result.AsyncResult
import usermanager.domain.transaction.TransactionRunner
import usermanager.domain.types.Id

import scala.concurrent.ExecutionContext

class SessionScenario @Inject()(
  sessionService: SessionService,
  @Named("cache.shade") implicit val transactionRunner: TransactionRunner
)(
  implicit ec: ExecutionContext,
) {

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
