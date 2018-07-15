package usermanager.domain.aggregates.session

import usermanager.domain.aggregates.sessionuser.{ SessionRepository, SessionUser }
import usermanager.domain.transaction.MockTransaction
import usermanager.domain.types.Id
import scalaz.{ -\/, \/- }
import usermanager.domain.error.DomainError
import usermanager.lib.error.Error
import usermanager.lib.error.transaction.delete.Deleted

class MockSessionRepository extends SessionRepository {

  override def find(sessionId: Id[SessionUser]): MockTransaction[Option[SessionUser]] = {
    MockTransaction { () =>
      sessionId match {
        case Id("NotFoundId") => \/-(None)
        case _ => \/-(Some(MockSessionUser()))
      }
    }
  }

  override def create(session: SessionUser): MockTransaction[Unit] = MockTransaction.from { () => () }

  override def delete(sessionId: Id[SessionUser]): MockTransaction[Deleted] = {
    MockTransaction { () =>
      sessionId match {
        case Id("NotFoundId") => \/-(false)
        case _ => \/-(true)
      }
    }
  }

}
