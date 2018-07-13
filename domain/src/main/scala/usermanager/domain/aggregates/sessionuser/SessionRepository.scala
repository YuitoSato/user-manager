package usermanager.domain.aggregates.sessionuser

import usermanager.domain.transaction.Transaction
import usermanager.domain.transaction.delete.Deleted
import usermanager.domain.types.Id

trait SessionRepository {

  def find(sessionId: Id[SessionUser]): Transaction[Option[SessionUser]]

  def create(session: SessionUser): Transaction[Unit]

  def delete(sessionId: Id[SessionUser]): Transaction[Deleted]

}
