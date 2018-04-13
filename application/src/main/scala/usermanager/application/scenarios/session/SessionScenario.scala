package usermanager.application.scenarios.session

import javax.inject.{ Inject, Named, Singleton }

import usermanager.application.services.session.SessionService
import usermanager.domain.aggregates.sessionuser.SessionUser
import usermanager.domain.result.Result
import usermanager.domain.transaction.TransactionRunner
import usermanager.domain.types.Id

import scala.concurrent.ExecutionContext

@Singleton
class SessionScenario @Inject()(
  sessionService: SessionService,
  @Named("cache.shade") implicit val transactionRunner: TransactionRunner
)(
  implicit ec: ExecutionContext,
) {

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
