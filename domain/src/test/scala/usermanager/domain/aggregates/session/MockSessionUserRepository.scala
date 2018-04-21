package usermanager.domain.aggregates.session

import usermanager.domain.aggregates.sessionuser.{ SessionUser, SessionUserRepository }
import usermanager.domain.error.DomainError
import usermanager.domain.transaction.MockTransaction
import usermanager.domain.transaction.delete.Deleted
import usermanager.domain.types.Id

import scalaz.{ -\/, \/- }

class MockSessionUserRepository extends SessionUserRepository {

  override def find(sessionId: Id[SessionUser]): MockTransaction[Option[SessionUser]] = {
    sessionId match {
      case Id("NotFoundId") => MockTransaction(-\/(DomainError.NotFound(SessionUser.TYPE, sessionId)))
      case _ => MockTransaction(\/-(Some(MockSessionUser())))
    }
  }

  override def create(session: SessionUser) = MockTransaction(\/-(()))

  override def delete(sessionId: Id[SessionUser]): MockTransaction[Deleted] = {
    sessionId match {
      case Id("NotFoundId") => MockTransaction(\/-(false))
      case _ => MockTransaction(\/-(true))
    }
  }

}
