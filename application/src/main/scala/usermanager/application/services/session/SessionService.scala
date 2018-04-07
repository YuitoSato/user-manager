package usermanager.application.services.session

import com.google.inject.name.Named
import com.google.inject.{ Inject, Singleton }
import usermanager.domain.error.{ DomainError, ErrorHandler }
import usermanager.domain.aggregates.sessionuser.{ SessionUser, SessionUserRepository }
import usermanager.domain.syntax.ToEitherOps
import usermanager.domain.transaction.async.{ AsyncTransaction, AsyncTransactionBuilder }
import usermanager.domain.transaction.sync.{ SyncTransaction, SyncTransactionBuilder }
import usermanager.domain.types.Id

import scala.concurrent.ExecutionContext

@Singleton
class SessionService @Inject()(
  sessionRepository: SessionUserRepository,
  @Named("cache.shade") implicit val asyncTransactionBuilder: AsyncTransactionBuilder,
  @Named("cache.shade") implicit val syncTransactionBuilder: SyncTransactionBuilder
)(
  implicit ec: ExecutionContext,
) extends ToEitherOps with ErrorHandler {

  def awaitFindById(sessionId: Id[SessionUser]): SyncTransaction[SessionUser] = {
    sessionRepository.awaitFind(sessionId) ifNotExists DomainError.NotFound("Session", sessionId)
  }

  def findById(sessionId: Id[SessionUser]): AsyncTransaction[SessionUser] = {
    sessionRepository.find(sessionId) ifNotExists DomainError.NotFound("Session", sessionId)
  }

  def create(session: SessionUser): AsyncTransaction[Unit] = {
    sessionRepository.create(session)
  }

  def delete(sessionId: Id[SessionUser]): AsyncTransaction[Unit] = {
    sessionRepository.delete(sessionId) ifFalse DomainError.NotFound("Session", sessionId)
  }

}
