package usermanager.domain.aggregates.sessionuser

import usermanager.domain.transaction.Transaction
import usermanager.domain.types.Id

trait SessionUserRepository {

  def find(sessionId: Id[SessionUser]): Transaction[Option[SessionUser]]

  def create(session: SessionUser): Transaction[Unit]

  def delete(sessionId: Id[SessionUser]): Transaction[Boolean]

}
