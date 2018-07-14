package usermanager.domain.aggregates.sessionuser

import usermanager.domain.types.Id
import usermanager.lib.error.transaction.delete.Deleted
import usermanager.lib.transaction.Transaction

trait SessionRepository {

  def find(sessionId: Id[SessionUser]): Transaction[Option[SessionUser]]

  def create(session: SessionUser): Transaction[Unit]

  def delete(sessionId: Id[SessionUser]): Transaction[Deleted]

}
