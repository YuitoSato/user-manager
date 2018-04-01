package usermanager.domain.session

import usermanager.domain.types.Id

import scala.concurrent.Future

trait SessionRepository {

  def find(sessionId: Id[Session]): Future[Option[Session]]

  def awaitFind(sessionId: Id[Session]): Option[Session]

  def create(session: Session): Future[Unit]

  def delete(sessionId: Id[Session]): Future[Boolean]

}
