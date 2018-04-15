package usermanager.application.services.session

import usermanager.domain.aggregates.sessionuser.{ SessionUser, SessionUserRepository }
import usermanager.domain.error.{ DomainError, ErrorHandler }
import usermanager.domain.syntax.ToEitherOps
import usermanager.domain.transaction.{ Transaction, TransactionBuilder }
import usermanager.domain.types.Id

trait SessionService extends ToEitherOps with ErrorHandler {

  val sessionRepository: SessionUserRepository
  implicit val transactionBuilder: TransactionBuilder

  def findById(sessionId: Id[SessionUser]): Transaction[SessionUser] = {
    sessionRepository.find(sessionId) ifNotExists DomainError.NotFound(SessionUser.TYPE, sessionId)
  }

  def create(session: SessionUser): Transaction[Unit] = {
    sessionRepository.create(session)
  }

  def delete(sessionId: Id[SessionUser]): Transaction[Unit] = {
    sessionRepository.delete(sessionId) ifFalse DomainError.NotFound(SessionUser.TYPE, sessionId)
  }

}
