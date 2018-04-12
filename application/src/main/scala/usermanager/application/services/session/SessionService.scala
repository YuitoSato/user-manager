package usermanager.application.services.session

import javax.inject.{ Inject, Named }

import usermanager.domain.aggregates.sessionuser.{ SessionUser, SessionUserRepository }
import usermanager.domain.error.{ DomainError, ErrorHandler }
import usermanager.domain.syntax.ToEitherOps
import usermanager.domain.transaction.{ Transaction, TransactionBuilder }
import usermanager.domain.types.Id

import scala.concurrent.ExecutionContext

class SessionService @Inject()(
  @Named("cache.shade") sessionRepository: SessionUserRepository,
  @Named("cache.shade") implicit val transactionBuilder: TransactionBuilder
)(
  implicit ec: ExecutionContext,
) extends ToEitherOps with ErrorHandler {

  def findById(sessionId: Id[SessionUser]): Transaction[SessionUser] = {
    sessionRepository.find(sessionId) ifNotExists DomainError.NotFound("Session", sessionId)
  }

  def create(session: SessionUser): Transaction[Unit] = {
    sessionRepository.create(session)
  }

  def delete(sessionId: Id[SessionUser]): Transaction[Unit] = {
    sessionRepository.delete(sessionId) ifFalse DomainError.NotFound("Session", sessionId)
  }

}
