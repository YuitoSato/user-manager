package usermanager.domain.session

import usermanager.domain.transaction.Transaction
import usermanager.domain.types.Id

trait SessionRepository {

  def findById(sessionId: Id[Session]): Transaction[Option[Session]]

  def create(session: Session): Transaction[Unit]

  def delete(sessionId: Id[Session]): Transaction[Unit]

}
