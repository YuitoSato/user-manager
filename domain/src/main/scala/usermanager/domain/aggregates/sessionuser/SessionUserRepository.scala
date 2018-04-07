package usermanager.domain.aggregates.sessionuser

import usermanager.domain.transaction.async.AsyncTransaction
import usermanager.domain.transaction.sync.SyncTransaction
import usermanager.domain.types.Id

trait SessionUserRepository {

  def awaitFind(sessionId: Id[SessionUser]): SyncTransaction[Option[SessionUser]]

  def find(sessionId: Id[SessionUser]): AsyncTransaction[Option[SessionUser]]

  def create(session: SessionUser): AsyncTransaction[Unit]

  def delete(sessionId: Id[SessionUser]): AsyncTransaction[Boolean]

}
