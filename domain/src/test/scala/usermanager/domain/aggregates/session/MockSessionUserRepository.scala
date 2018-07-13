package usermanager.domain.aggregates.session

import usermanager.domain.aggregates.sessionuser.{ SessionUser, SessionUserRepository }
import usermanager.domain.error.Error
import usermanager.domain.transaction.MockTransaction
import usermanager.domain.transaction.delete.Deleted
import usermanager.domain.types.Id

import scalaz.{ -\/, \/- }

class MockSessionUserRepository extends SessionUserRepository {

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
