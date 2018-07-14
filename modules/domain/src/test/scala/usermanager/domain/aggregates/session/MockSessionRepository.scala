package usermanager.domain.aggregates.session

import usermanager.domain.aggregates.sessionuser.{ SessionRepository, SessionUser }
import usermanager.domain.transaction.MockTransaction
import usermanager.domain.types.Id
import scalaz.{ -\/, \/- }
import usermanager.lib.error.Error
import usermanager.lib.error.transaction.delete.Deleted

class MockSessionRepository extends SessionRepository {

  override def find(sessionId: Id[SessionUser]): MockTransaction[Option[SessionUser]] = {
    MockTransaction { () =>
      sessionId match {
        case Id("NotFoundId") => -\/(Error.NotFound(SessionUser.TYPE, sessionId))
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
